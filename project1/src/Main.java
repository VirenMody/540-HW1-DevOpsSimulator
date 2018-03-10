
import org.eclipse.egit.github.core.*;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.*;
import com.google.gson.*;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabProject;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;


import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println("Hello.");

        GitHubClient client = new GitHubClient();
        client.setOAuth2Token("80e841e804324f06e4755b7f05066f9dd0443e47");
        System.out.println("Remaining requests: " + client.getRequestLimit());

        // Download code from GitHub
        RepositoryService service = new RepositoryService();
        service.getClient();

        ContentsService contentsService = new ContentsService();

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
            System.out.println("Homepage: " + searchRepo.getHomepage());

            //Able to get repository URL so I can download repository using JGit
            System.out.println("URL: " + searchRepo.getUrl());
            System.out.println("To String: " + searchRepo.toString());
            System.out.println("Owner: " + searchRepo.getOwner());
            System.out.println("Name: " + searchRepo.getName());
            System.out.println("ID: " + searchRepo.getId());

            RepositoryId repositoryId = new RepositoryId(searchRepo.getOwner(), searchRepo.getName());
            System.out.println("Repo ID: " + repositoryId.toString());
            try {
                List<RepositoryContents> repositoryContents = contentsService.getContents(repositoryId);
                for (RepositoryContents repo: repositoryContents){
                    System.out.println("Get Name: " + repo.getName());
                }

            }
            catch (IOException e){
                System.err.println("Caught IOException: " + e.getMessage());
            }
        }


        String gitlabHostUrl = "http://10.0.2.15";
        String apiAccessToken = "RDtCnzMezmjpih4u6VDm";
        // Retrieve Gitlab API Access
        GitlabAPI gitlabAPI = GitlabAPI.connect(gitlabHostUrl, apiAccessToken);
        try {
            System.out.println("Current Number of Projects: " + gitlabAPI.getProjects().size());
        } catch (IOException e) {
            e.printStackTrace();
        }

//        // Retrieve Gitlab API Access
//        GitLabApi gitLabApi = null;
//        try {
//            gitLabApi = new GitLabApi(GitLabApi.ApiVersion.V3, "http://10.0.2.15/", "RDtCnzMezmjpih4u6VDm");
//            System.out.println("gitLabApi = " + gitLabApi);
//        } catch (Exception e) {
//            System.err.println("Caught Exception: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        // Get the list of projects your account has access to
//        try {
//            List<Project> projects = gitLabApi.getProjectApi().getProjects();
//        } catch (GitLabApiException e) {
//            System.err.println("Caught Exception: " + e.getMessage());
//            //e.printStackTrace();
//        }


        // Create a new project
        GitlabProject newProject = null;
        try {
            newProject = gitlabAPI.createProject("test 2");
        } catch (Exception e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        //System.out.println("Current Number of Projects: " + gitlabAPI.getProjects().size());
        System.out.println("Added project: " + newProject.getName());

        // Create a new project with a given repository URL
        GitlabProject newProject2 = null;
        try {
            newProject2 = gitlabAPI.createProject("Maven-Hello-Imported");
        } catch (Exception e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        //System.out.println("Current Number of Projects: " + gitlabAPI.getProjects().size());
        System.out.println("Added project: " + newProject2.getName());
    }
}