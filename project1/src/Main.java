import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.Job;

import org.eclipse.egit.github.core.*;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.*;
import com.google.gson.*;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
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


import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.*;
import javax.xml.parsers.*;


public class Main {

    public static void main(String[] args) {


        String jobXml = "<project><actions/><description/><keepDependencies>false</keepDependencies><properties><jenkins.model.BuildDiscarderProperty><strategy class=\"hudson.tasks.LogRotator\"><daysToKeep>-1</daysToKeep><numToKeep>3</numToKeep><artifactDaysToKeep>-1</artifactDaysToKeep><artifactNumToKeep>-1</artifactNumToKeep></strategy></jenkins.model.BuildDiscarderProperty><com.dabsquared.gitlabjenkins.connection.GitLabConnectionProperty plugin=\"gitlab-plugin@1.5.3\"><gitLabConnection>GitlabViren</gitLabConnection></com.dabsquared.gitlabjenkins.connection.GitLabConnectionProperty><org.jenkinsci.plugins.gitlablogo.GitlabLogoProperty plugin=\"gitlab-logo@1.0.3\"><repositoryName>Administrator/540-DevOps-Simulation</repositoryName></org.jenkinsci.plugins.gitlablogo.GitlabLogoProperty></properties><scm class=\"hudson.plugins.git.GitSCM\" plugin=\"git@3.8.0\"><configVersion>2</configVersion><userRemoteConfigs><hudson.plugins.git.UserRemoteConfig><url>http://10.0.2.15/root/540-DevOps-Simulation.git</url><credentialsId>449eaaad-4ce3-4cd7-88e4-fbda5b6cb318</credentialsId></hudson.plugins.git.UserRemoteConfig></userRemoteConfigs><branches><hudson.plugins.git.BranchSpec><name>*/master</name></hudson.plugins.git.BranchSpec></branches><doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations><browser class=\"hudson.plugins.git.browser.GitLab\"><url>http://10.0.2.15/root/540-DevOps-Simulation</url><version>10.5</version></browser><submoduleCfg class=\"list\"/><extensions/></scm><canRoam>true</canRoam><disabled>false</disabled><blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding><blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding><triggers><com.dabsquared.gitlabjenkins.GitLabPushTrigger plugin=\"gitlab-plugin@1.5.3\"><spec/><triggerOnPush>true</triggerOnPush><triggerOnMergeRequest>true</triggerOnMergeRequest><triggerOnPipelineEvent>false</triggerOnPipelineEvent><triggerOnAcceptedMergeRequest>false</triggerOnAcceptedMergeRequest><triggerOnClosedMergeRequest>false</triggerOnClosedMergeRequest><triggerOnApprovedMergeRequest>true</triggerOnApprovedMergeRequest><triggerOpenMergeRequestOnPush>never</triggerOpenMergeRequestOnPush><triggerOnNoteRequest>true</triggerOnNoteRequest><noteRegex>Jenkins please retry a build</noteRegex><ciSkip>true</ciSkip><skipWorkInProgressMergeRequest>true</skipWorkInProgressMergeRequest><setBuildDescription>true</setBuildDescription><branchFilterType>All</branchFilterType><includeBranchesSpec/><excludeBranchesSpec/><targetBranchRegex/><secretToken>{AQAAABAAAAAQaaBNEMvEXKTXZ8mdW7/jX86kI413u5ByjTgaQKU7jTk=}</secretToken></com.dabsquared.gitlabjenkins.GitLabPushTrigger></triggers><concurrentBuild>false</concurrentBuild><builders/><publishers><com.dabsquared.gitlabjenkins.publisher.GitLabCommitStatusPublisher plugin=\"gitlab-plugin@1.5.3\"><name>jenkins</name><markUnstableAsSuccess>false</markUnstableAsSuccess></com.dabsquared.gitlabjenkins.publisher.GitLabCommitStatusPublisher></publishers><buildWrappers><hudson.plugins.timestamper.TimestamperBuildWrapper plugin=\"timestamper@1.8.9\"/></buildWrappers></project>";

        // XML parser code from tutorial:
        // https://www.ibm.com/developerworks/library/j-pg05199/index.html
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(jobXml));
            Document doc = db.parse(inputSource);

            //Traversal code from Stack Overflow:
            //  https://stackoverflow.com/questions/5386991/java-most-efficient-method-to-iterate-over-all-elements-in-a-org-w3c-dom-docume
            //  Go through the XML string, print every tag
            NodeList nodeList = doc.getElementsByTagName("*");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    //Print every tag
                    System.out.println(node.getNodeName());
                    //Print all the contents of the tags
                    System.out.println(node.getTextContent());
                    //Note: There is also a setTextContent that changes the tag content!
                }
            }

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        /*

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


        //TODO Update following variables based on repositories from Github search
//        String projectName = "maven-helloworld";
//        String username = "amuniz";
        String projectName = "simple-java-maven-app";
        String username = "jenkins-docs";
//        String projectName = "maven-helloworld";
//        String username = "amuniz";
//        String projectName = "maven-helloworld";
//        String username = "amuniz";
        String repoURL = "https://github.com/" + username + "/" + projectName + ".git";
        //String repoURL = "https://github.com/guillermokrh/simple-java-maven-app";
        String LOCAL_PATH = "/home/virenmody/ClonedRepos/" + projectName;
        //String LOCAL_PATH = "/Users/guillermo/cs540/jgit_testing/";

        // Clone the Github repository (RESOURCE: http://www.vogella.com/tutorials/JGit/article.html)
        Git clonedRepo = null;
        try {
            System.out.println("Cloning GitHub repo " + projectName + " to local directory: " + LOCAL_PATH);
            clonedRepo = Git.cloneRepository()
                    .setURI(repoURL)
                    .setDirectory(new File(LOCAL_PATH))
                    .call();
        } catch (GitAPIException e) {
            System.err.println("Caught GitAPIException: " + e.getMessage());
        }

        //TODO Please update the following for your setup
        String gitlabHostUrl = "http://10.0.2.15";
        String apiAccessToken = "RDtCnzMezmjpih4u6VDm";

        // Retrieve Gitlab API Instance
        GitLabApi gitLabApi = null;
        try {
            gitLabApi = new GitLabApi(GitLabApi.ApiVersion.V3, gitlabHostUrl, apiAccessToken);
            System.out.println("Gitlab API Instance = " + gitLabApi);
        } catch (Exception e) {
            System.err.println("Caught Exception for Gitlab API Access: " + e.getMessage());
            e.printStackTrace();
        }

        // Create a new empty project on Gitlab
        Project newProject = null;
        ProjectApi projectApi = gitLabApi.getProjectApi();
        //TODO First check if this project name already exists in Gitlab
        String projectDescription = "Pulled from Github.com: " + username + " : " + projectName;
        //String importUrl = "https://github.com/amuniz/maven-helloworld.git";
        System.out.println("Creating Gitlab Project: " + projectName + " at " + gitlabHostUrl + "/root/"+projectName);
        try {
            newProject = projectApi.createProject(projectName, null, projectDescription, null, null, null, null, Visibility.PUBLIC, null, null);
            //newProject = projectApi.createProject(projectName, null, projectDescription, null, null, null, null, Visibility.PUBLIC, null, importUrl);
        } catch (GitLabApiException e) {
            System.err.println("Caught GitlabApiException for Project Creation: " + e.getMessage());
        }

        // Create a Jenkins webhook for the project
        //TODO Please update the localhost/ip:port number according to your Jenkins setup (localhost = 10.0.2.15)
        String jenkinsHostUrl = "http://10.0.2.15:8081";
        //String webhookUrl = jenkinsHostUrl + "/project/" + projectName;
        //String webhookUrl = jenkinsHostUrl + "/project/Maven-Hello-TestImport2";
        String webhookUrl = jenkinsHostUrl + "/project/simple-java-maven-app";
        ProjectHook webhook = null;
        System.out.println("Creating Gitlab to Jenkins webhook: " + webhookUrl);
        try {
            webhook = projectApi.addHook(newProject, webhookUrl, true, false, false);
        } catch (GitLabApiException e) {
            System.err.println("Caught GitlabApiException for Webhook: " + e.getMessage());
        }

        // Create a Jenkins Job for the project
        // TODO Please update the username and password according to your Jenkins setup
        String jenkinsUsername = "admin";
        String jenkinsPassword = "admin";
        System.out.println("Creating a Jenkins job for this project: " + jenkinsHostUrl + "/job/" + projectName);
        try {
            URI jenkinsUri = new URI(jenkinsHostUrl + "/");
            JenkinsServer jenkins = null;
            jenkins = new JenkinsServer(jenkinsUri, jenkinsUsername, jenkinsPassword);
            System.out.println("\t - Jenkins Running?: " + jenkins.isRunning()  );
            //jenkins.createJob("JenkinsApiTest", jobXml);
            Map<String, Job> jobs = jenkins.getJobs();
            System.out.println("\t - Jobs: " + jobs);

        } catch (URISyntaxException e) {
            System.err.println("Caught URISyntaxException for Jenkins Server Retrieval: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Caught IOException for Jenkins jobs retrieval: " + e.getMessage());
            e.printStackTrace();
        }


        // Push repository to our local Gitlab server
        System.out.println("Pushing project to Gitlab repository: " + projectName);
        try {
            //  Remove origin from repo (RESOURCE: https://stackoverflow.com/questions/12799573/add-remote-via-jgit)
            System.out.println("\t - Removing remote Github repo as origin from repo");
            StoredConfig config = clonedRepo.getRepository().getConfig();
            config.unsetSection("remote", "origin");
            config.save();

            // Push the repo to Gitlab (Resource: https://stackoverflow.com/questions/13446842/how-do-i-do-git-push-with-jgit)
            System.out.println("\t - Adding remote Gitlab repo as origin to repo");

            RemoteAddCommand remoteAddCommand = clonedRepo.remoteAdd();
            remoteAddCommand.setName("origin");
            remoteAddCommand.setUri(new URIish(gitlabHostUrl + "/root/" + projectName + ".git"));
            remoteAddCommand.call();

            System.out.println("\t - Pushing repo with proper credentials to Gitlab Server");
            // TODO Please update Gitlab username and password according to your Gitlab setup
            String gitLabUsername = "root";
            String gitLabPassword = "rootroot";
            UsernamePasswordCredentialsProvider credProvider = new UsernamePasswordCredentialsProvider(gitLabUsername, gitLabPassword);
            PushCommand pushCommand = clonedRepo.push();
            pushCommand.setCredentialsProvider(credProvider);
            pushCommand.call();

        } catch (GitAPIException e) {
            System.err.println("Caught GitAPIException: " + e.getMessage());
        } catch (URISyntaxException e) {
            System.err.println("Caught URISyntaxException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        */
    }
}
