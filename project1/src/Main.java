
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.Job;
import org.eclipse.egit.github.core.*;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.*;
import com.google.gson.*;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabProject;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.ProjectApi;
import org.gitlab4j.api.models.Project;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.io.*;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.gitlab4j.api.models.ProjectHook;
import org.gitlab4j.api.models.Visibility;

public class Main {

    public static void main(String[] args) {
        // write your code here
/*
        System.out.println("Hello.");

        GitHubClient client = new GitHubClient();
        client.setOAuth2Token("80e841e804324f06e4755b7f05066f9dd0443e47");
        System.out.println("Remaining requests: " + client.getRequestLimit());

        // Download code from GitHub
        RepositoryService service = new RepositoryService();
        service.getClient();

        ContentsService contentsService = new ContentsService();


        String java_query = "language:java";
        //Query searches for the simple-java-maven-app project inside the jenkins-docs org
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

            //Create RepositoryID object based on current repo being searched
            RepositoryId repositoryId = new RepositoryId(searchRepo.getOwner(), searchRepo.getName());
            System.out.println("Repo ID: " + repositoryId.toString());
            try {
                //Get the repository contents of that repo
                List<RepositoryContents> repositoryContents = contentsService.getContents(repositoryId);

                //Print the file names of the contents of the repo
                for (RepositoryContents repo: repositoryContents){
                    System.out.println("Get Name: " + repo.getName());
                }

            }
            catch (IOException e){
                System.err.println("Caught IOException: " + e.getMessage());
            }
        }


        //Beginning of JGit library usage
        String REMOTE_URL = "https://github.com/guillermokrh/simple-java-maven-app";
        String LOCAL_PATH = "/Users/guillermo/cs540/jgit_testing/";
        System.out.println("Cloning from " + REMOTE_URL + " to " + LOCAL_PATH);

        File localPath = new File(LOCAL_PATH);

        try (Git result = Git.cloneRepository()
                .setURI(REMOTE_URL)
                .setDirectory(localPath)
                .call()) {
            // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
            System.out.println("Repository Made: " + result.getRepository().getDirectory());
        } catch(GitAPIException g){
            System.out.println(g);
        }

        String gitlabHostUrl = "http://10.0.2.15";
        String apiAccessToken = "RDtCnzMezmjpih4u6VDm";

        // Retrieve Gitlab API Access
        GitLabApi gitLabApi = null;
        try {
            gitLabApi = new GitLabApi(GitLabApi.ApiVersion.V3, gitlabHostUrl, apiAccessToken);
            System.out.println("gitLabApi = " + gitLabApi);
        } catch (Exception e) {
            System.err.println("Caught Exception for Gitlab API Access: " + e.getMessage());
            e.printStackTrace();
        }

        // Create a new project on Gitlab and import from existing Github Repository
        Project newProject = null;
        ProjectApi projectApi = gitLabApi.getProjectApi();
        String projectName = "test project name";
        String projectDescription = "Test Description";
        String importUrl = "https://github.com/amuniz/maven-helloworld.git";
        try {
            newProject = projectApi.createProject(projectName, null, projectDescription, null, null, null, null, Visibility.PUBLIC, null, importUrl);
        } catch (GitLabApiException e) {
            System.err.println("Caught GitlabApiException for Project Creation: " + e.getMessage());
        }

        // Create a webhook for the project
        String webhookUrl = "http://localhost:8081/project/Maven-Hello-TestImport2";
        ProjectHook webhook = null;
        try {
            webhook = projectApi.addHook(newProject, webhookUrl, true, false, false);
        } catch (GitLabApiException e) {
            System.err.println("Caught GitlabApiException for Webhook: " + e.getMessage());
        }
        System.out.println("Webhook: " + webhook);
*/

        // Create a Jenkins Job for the project
        try {
            URI jenkinsUri = new URI("http://localhost:8081/");
            JenkinsServer jenkins = null;
            jenkins = new JenkinsServer(jenkinsUri, "admin", "admin");
            System.out.println("Jenkins reference: " + jenkins.isRunning()  );
            String jobXml = "<project><actions/><description/><keepDependencies>false</keepDependencies><properties><jenkins.model.BuildDiscarderProperty><strategy class=\"hudson.tasks.LogRotator\"><daysToKeep>-1</daysToKeep><numToKeep>3</numToKeep><artifactDaysToKeep>-1</artifactDaysToKeep><artifactNumToKeep>-1</artifactNumToKeep></strategy></jenkins.model.BuildDiscarderProperty><com.dabsquared.gitlabjenkins.connection.GitLabConnectionProperty plugin=\"gitlab-plugin@1.5.3\"><gitLabConnection>GitlabViren</gitLabConnection></com.dabsquared.gitlabjenkins.connection.GitLabConnectionProperty><org.jenkinsci.plugins.gitlablogo.GitlabLogoProperty plugin=\"gitlab-logo@1.0.3\"><repositoryName>Administrator/540-DevOps-Simulation</repositoryName></org.jenkinsci.plugins.gitlablogo.GitlabLogoProperty></properties><scm class=\"hudson.plugins.git.GitSCM\" plugin=\"git@3.8.0\"><configVersion>2</configVersion><userRemoteConfigs><hudson.plugins.git.UserRemoteConfig><url>http://10.0.2.15/root/540-DevOps-Simulation.git</url><credentialsId>449eaaad-4ce3-4cd7-88e4-fbda5b6cb318</credentialsId></hudson.plugins.git.UserRemoteConfig></userRemoteConfigs><branches><hudson.plugins.git.BranchSpec><name>*/master</name></hudson.plugins.git.BranchSpec></branches><doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations><browser class=\"hudson.plugins.git.browser.GitLab\"><url>http://10.0.2.15/root/540-DevOps-Simulation</url><version>10.5</version></browser><submoduleCfg class=\"list\"/><extensions/></scm><canRoam>true</canRoam><disabled>false</disabled><blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding><blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding><triggers><com.dabsquared.gitlabjenkins.GitLabPushTrigger plugin=\"gitlab-plugin@1.5.3\"><spec/><triggerOnPush>true</triggerOnPush><triggerOnMergeRequest>true</triggerOnMergeRequest><triggerOnPipelineEvent>false</triggerOnPipelineEvent><triggerOnAcceptedMergeRequest>false</triggerOnAcceptedMergeRequest><triggerOnClosedMergeRequest>false</triggerOnClosedMergeRequest><triggerOnApprovedMergeRequest>true</triggerOnApprovedMergeRequest><triggerOpenMergeRequestOnPush>never</triggerOpenMergeRequestOnPush><triggerOnNoteRequest>true</triggerOnNoteRequest><noteRegex>Jenkins please retry a build</noteRegex><ciSkip>true</ciSkip><skipWorkInProgressMergeRequest>true</skipWorkInProgressMergeRequest><setBuildDescription>true</setBuildDescription><branchFilterType>All</branchFilterType><includeBranchesSpec/><excludeBranchesSpec/><targetBranchRegex/><secretToken>{AQAAABAAAAAQaaBNEMvEXKTXZ8mdW7/jX86kI413u5ByjTgaQKU7jTk=}</secretToken></com.dabsquared.gitlabjenkins.GitLabPushTrigger></triggers><concurrentBuild>false</concurrentBuild><builders/><publishers><com.dabsquared.gitlabjenkins.publisher.GitLabCommitStatusPublisher plugin=\"gitlab-plugin@1.5.3\"><name>jenkins</name><markUnstableAsSuccess>false</markUnstableAsSuccess></com.dabsquared.gitlabjenkins.publisher.GitLabCommitStatusPublisher></publishers><buildWrappers><hudson.plugins.timestamper.TimestamperBuildWrapper plugin=\"timestamper@1.8.9\"/></buildWrappers></project>";
            jenkins.createJob("JenkinsApiTest", jobXml);
            Map<String, Job> jobs = jenkins.getJobs();
            System.out.println("jobs: " + jobs);

        } catch (URISyntaxException e) {
            System.err.println("Caught URISyntaxException for Jenkins Server Retrieval: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Caught IOException for Jenkins jobs retrieval: " + e.getMessage());
            e.printStackTrace();
        }

    }
}