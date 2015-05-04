
package edu.tum.cs.aicos.linkeddata.training.api;

import org.openrdf.model.Literal;
import org.openrdf.query.*;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;

import java.util.ArrayList;

/**
 * Created by Pat on 04.05.2015.
 */
public class Countries extends ArrayList<Country> {

    public Countries(String population) throws RepositoryException, MalformedQueryException, QueryEvaluationException {


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
                        "FILTER(?population >=" + population + ")\n " +
                        "} \n " +
                        "ORDER BY (?population)\n " +
                        "LIMIT 20\n "


        );


        TupleQueryResult tupleQueryResult = tupleQuery.evaluate();
        while (tupleQueryResult.hasNext()) {
            BindingSet bindingSet = tupleQueryResult.next();

            Country country = new Country();

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


            this.add(country);
        }
        tupleQueryResult.close();

        repositoryConnection.close();

        httpRepository.shutDown();
    }
}