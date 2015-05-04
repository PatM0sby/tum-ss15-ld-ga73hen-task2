package edu.tum.cs.aicos.linkeddata.training.api;

import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.query.*;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/")
public class ApiController {

    Logger logger = LoggerFactory.getLogger(ApiController.class);

    @RequestMapping(value = "/cities")
    public Cities loadGermanCities() throws RepositoryException, MalformedQueryException, QueryEvaluationException {
        logger.debug("Loading list of german cities from DBpedia...");

        // Load list of scientists from DBpedia
        HTTPRepository httpRepository = new HTTPRepository("http://dbpedia.org/sparql");
        httpRepository.initialize();

        RepositoryConnection repositoryConnection = httpRepository.getConnection();

        TupleQuery tupleQuery = repositoryConnection.prepareTupleQuery(QueryLanguage.SPARQL,
                "SELECT ?cityURI ?city ?populationTotal\n " +
                        "WHERE {\n" +
                        "   ?cityURI <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://schema.org/City> .\n" +
                        "   ?cityURI <http://dbpedia.org/ontology/country> <http://dbpedia.org/resource/Germany> .\n" +
                        "\n" +
                        "   ?cityURI <http://dbpedia.org/ontology/populationTotal> ?populationTotal.\n" +
                        "\n" +
                        "   ?cityURI rdfs:label ?city .\n" +
                        "   FILTER (lang(?city)=\"de\")\n" +
                        "}\n" +
                        "ORDER BY DESC (?populationTotal)\n" +
                        "LIMIT 20");

        Cities cities = new Cities();
        TupleQueryResult tupleQueryResult = tupleQuery.evaluate();
        while (tupleQueryResult.hasNext()) {
            BindingSet bindingSet = tupleQueryResult.next();

            City city = new City();

            Binding uriBinding = bindingSet.getBinding("cityURI");
            URI uri = (URI) uriBinding.getValue();
            city.setUri(uri.stringValue());

            Binding labelBinding = bindingSet.getBinding("city");
            Literal labelLiteral = (Literal) labelBinding.getValue();
            String labelString = labelLiteral.stringValue();
            city.setLabel(labelString);

            Binding populationBinding = bindingSet.getBinding("populationTotal");
            Literal populationLiteral = (Literal) populationBinding.getValue();
            BigInteger populationBigInteger = populationLiteral.integerValue();
            int populationInt = populationBigInteger.intValue();
            city.setPopulation(populationInt);

            cities.add(city);
        }
        tupleQueryResult.close();

        repositoryConnection.close();

        httpRepository.shutDown();

        logger.debug("Loaded list of german cities from DBpedia.");

        return cities;
    }


    @RequestMapping(value = "/countries")
    public Countries loadCountries() throws RepositoryException, MalformedQueryException, QueryEvaluationException {
        logger.debug("Loading list of german cities from DBpedia...");

        // Load list of scientists from DBpedia
        HTTPRepository httpRepository = new HTTPRepository("http://dbpedia.org/sparql");
        httpRepository.initialize();

        RepositoryConnection repositoryConnection = httpRepository.getConnection();

        TupleQuery tupleQuery = repositoryConnection.prepareTupleQuery(QueryLanguage.SPARQL,


                        "SELECT ?name ?capital ?currency\n " +
                        "WHERE {\n" +

                        "?c a <http://dbpedia.org/ontology/Country> .\n " +
                        "?c rdfs:label ?name.\n " +
                        "?c dbpedia-owl:capital ?y.\n " +
                        "?y rdfs:label ?capital. \n " +
                        "?c dbpedia-owl:populationTotal ?population.\n " +
                        "?c dbpedia-owl:currency ?z.\n " +
                        "?z rdfs:label ?currency.\n " +
                        "FILTER(langmatches(lang(?name),\"EN\"))\n " +
                        "FILTER(langmatches(lang(?capital),\"EN\"))\n " +
                        "FILTER(langmatches(lang(?currency),\"EN\"))\n " +
                        "FILTER(?population > 1000)\n " +
                        "} \n " +
                        "ORDER BY (?population)\n " +
                        "LIMIT 10000\n "




                        );

        Cities cities = new Cities();
        Countries countries = new Countries();
        TupleQueryResult tupleQueryResult = tupleQuery.evaluate();
        while (tupleQueryResult.hasNext()) {
            BindingSet bindingSet = tupleQueryResult.next();

            Country country=new Country();

            Binding nameBinding = bindingSet.getBinding("name");
            Literal nameLiteral = (Literal) nameBinding.getValue();
            String nameString = nameLiteral.stringValue();
            country.setName(nameString);

            Binding capitalBinding = bindingSet.getBinding("capital");
            Literal capitalLiteral = (Literal) capitalBinding.getValue();
            String capitalString = capitalLiteral.stringValue();
            country.setCapital(capitalString);

            Binding populationBinding = bindingSet.getBinding("currency");
            Literal populationLiteral = (Literal) populationBinding.getValue();
            String populationString = populationLiteral.stringValue();
            country.setCurrency(populationString);


            countries.add(country);
        }
        tupleQueryResult.close();

        repositoryConnection.close();

        httpRepository.shutDown();

        logger.debug("Loaded list of german cities from DBpedia.");

        return countries;
    }

}

