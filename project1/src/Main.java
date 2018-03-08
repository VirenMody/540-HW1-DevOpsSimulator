
import org.eclipse.egit.github.core.*;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.*;
import com.google.gson.*;
import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println("Hello.");

        GitHubClient client = new GitHubClient();
        System.out.println("Remaining requests: " + client.getRequestLimit());

        // Download code from GitHub
        RepositoryService service = new RepositoryService();
        service.getClient();


        /*
        List<Repository> repositoryList = null;
        try {
            repositoryList = service.getRepositories("guillermokrh");
        }
        catch (IOException e){
            System.err.println("Caught IOException: " + e.getMessage());
        }
        for (Repository repo : repositoryList)
            System.out.println(repo.getName() + " Watchers: " + repo.getWatchers());
        */

        String java_query = "language:java";
        String query = "simple-java-maven-app org:jenkins-docs";
        List<SearchRepository> searchRepositoryList = null;

        try {
            searchRepositoryList = service.searchRepositories(query);
        }
        catch (IOException e){
            System.err.println("Caught IOException: " + e.getMessage());
        }

        for (SearchRepository searchRepo: searchRepositoryList) {
            //If repository contains a Jenkinsfile, download the repository
            System.out.println(searchRepo.getName() + " Watchers: " + searchRepo.getWatchers());
        }
    }
}
