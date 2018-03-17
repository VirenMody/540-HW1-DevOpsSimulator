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
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Transformer;



public class Main {

    public static String customJenkinsJobXML(String gitlabHostUrl, String projectName){

        //String jobXml = "<project><actions/><description>This is a free style job</description><keepDependencies>false</keepDependencies><properties><jenkins.model.BuildDiscarderProperty><strategy class=\"hudson.tasks.LogRotator\"><daysToKeep>-1</daysToKeep><numToKeep>1</numToKeep><artifactDaysToKeep>-1</artifactDaysToKeep><artifactNumToKeep>-1</artifactNumToKeep></strategy></jenkins.model.BuildDiscarderProperty><com.dabsquared.gitlabjenkins.connection.GitLabConnectionProperty plugin=\"gitlab-plugin@1.5.3\"><gitLabConnection>GitlabViren</gitLabConnection></com.dabsquared.gitlabjenkins.connection.GitLabConnectionProperty><org.jenkinsci.plugins.gitlablogo.GitlabLogoProperty plugin=\"gitlab-logo@1.0.3\"><repositoryName/></org.jenkinsci.plugins.gitlablogo.GitlabLogoProperty></properties><scm class=\"hudson.plugins.git.GitSCM\" plugin=\"git@3.8.0\"><configVersion>2</configVersion><userRemoteConfigs><hudson.plugins.git.UserRemoteConfig><name>origin</name><refspec>+refs/heads/*:refs/remotes/origin/* +refs/merge-requests/*/head:refs/remotes/origin/merge-requests/*</refspec><url>http://10.0.2.15/root/example-groovy.git</url><credentialsId>449eaaad-4ce3-4cd7-88e4-fbda5b6cb318</credentialsId></hudson.plugins.git.UserRemoteConfig></userRemoteConfigs><branches><hudson.plugins.git.BranchSpec><name>*/master</name></hudson.plugins.git.BranchSpec></branches><doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations><browser class=\"hudson.plugins.git.browser.GitLab\"><url>http://10.0.2.15/root/example-groovy</url><version>10.5</version></browser><submoduleCfg class=\"list\"/><extensions/></scm><canRoam>true</canRoam><disabled>false</disabled><blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding><blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding><triggers><com.dabsquared.gitlabjenkins.GitLabPushTrigger plugin=\"gitlab-plugin@1.5.3\"><spec/><triggerOnPush>true</triggerOnPush><triggerOnMergeRequest>true</triggerOnMergeRequest><triggerOnPipelineEvent>false</triggerOnPipelineEvent><triggerOnAcceptedMergeRequest>true</triggerOnAcceptedMergeRequest><triggerOnClosedMergeRequest>true</triggerOnClosedMergeRequest><triggerOnApprovedMergeRequest>true</triggerOnApprovedMergeRequest><triggerOpenMergeRequestOnPush>never</triggerOpenMergeRequestOnPush><triggerOnNoteRequest>true</triggerOnNoteRequest><noteRegex>Jenkins please retry a build</noteRegex><ciSkip>true</ciSkip><skipWorkInProgressMergeRequest>true</skipWorkInProgressMergeRequest><setBuildDescription>true</setBuildDescription><branchFilterType>All</branchFilterType><includeBranchesSpec/><excludeBranchesSpec/><targetBranchRegex/><secretToken>{AQAAABAAAAAQd9GNVd4u7oQlWZAHd3dYcsfoOx1jXSPneEY7Mp5xX2I=}</secretToken></com.dabsquared.gitlabjenkins.GitLabPushTrigger></triggers><concurrentBuild>false</concurrentBuild><builders><hudson.tasks.Maven><targets>clean install</targets><usePrivateRepository>false</usePrivateRepository><settings class=\"jenkins.mvn.DefaultSettingsProvider\"/><globalSettings class=\"jenkins.mvn.DefaultGlobalSettingsProvider\"/><injectBuildVariables>false</injectBuildVariables></hudson.tasks.Maven></builders><publishers><hudson.tasks.junit.JUnitResultArchiver plugin=\"junit@1.24\"><testResults>target/surefire-reports/*.xml</testResults><keepLongStdio>false</keepLongStdio><healthScaleFactor>1.0</healthScaleFactor><allowEmptyResults>true</allowEmptyResults></hudson.tasks.junit.JUnitResultArchiver><hudson.plugins.jacoco.JacocoPublisher plugin=\"jacoco@3.0.1\"><execPattern>**/**.exec</execPattern><classPattern>**/classes</classPattern><sourcePattern>**/src/main/java</sourcePattern><inclusionPattern/><exclusionPattern/><skipCopyOfSrcFiles>false</skipCopyOfSrcFiles><minimumInstructionCoverage>0</minimumInstructionCoverage><minimumBranchCoverage>0</minimumBranchCoverage><minimumComplexityCoverage>0</minimumComplexityCoverage><minimumLineCoverage>0</minimumLineCoverage><minimumMethodCoverage>0</minimumMethodCoverage><minimumClassCoverage>0</minimumClassCoverage><maximumInstructionCoverage>0</maximumInstructionCoverage><maximumBranchCoverage>0</maximumBranchCoverage><maximumComplexityCoverage>0</maximumComplexityCoverage><maximumLineCoverage>0</maximumLineCoverage><maximumMethodCoverage>0</maximumMethodCoverage><maximumClassCoverage>0</maximumClassCoverage><changeBuildStatus>false</changeBuildStatus><deltaInstructionCoverage>0</deltaInstructionCoverage><deltaBranchCoverage>0</deltaBranchCoverage><deltaComplexityCoverage>0</deltaComplexityCoverage><deltaLineCoverage>0</deltaLineCoverage><deltaMethodCoverage>0</deltaMethodCoverage><deltaClassCoverage>0</deltaClassCoverage><buildOverBuild>false</buildOverBuild></hudson.plugins.jacoco.JacocoPublisher><com.dabsquared.gitlabjenkins.publisher.GitLabCommitStatusPublisher plugin=\"gitlab-plugin@1.5.3\"><name>jenkins</name><markUnstableAsSuccess>false</markUnstableAsSuccess></com.dabsquared.gitlabjenkins.publisher.GitLabCommitStatusPublisher></publishers><buildWrappers><hudson.plugins.ws__cleanup.PreBuildCleanup plugin=\"ws-cleanup@0.34\"><deleteDirs>false</deleteDirs><cleanupParameter/><externalDelete/></hudson.plugins.ws__cleanup.PreBuildCleanup><hudson.plugins.timestamper.TimestamperBuildWrapper plugin=\"timestamper@1.8.9\"/></buildWrappers></project>";
        String jobXml = "<project><actions/><description>This is the tutorial free style job</description><keepDependencies>false</keepDependencies><properties><jenkins.model.BuildDiscarderProperty><strategy class=\"hudson.tasks.LogRotator\"><daysToKeep>-1</daysToKeep><numToKeep>1</numToKeep><artifactDaysToKeep>-1</artifactDaysToKeep><artifactNumToKeep>-1</artifactNumToKeep></strategy></jenkins.model.BuildDiscarderProperty><com.dabsquared.gitlabjenkins.connection.GitLabConnectionProperty plugin=\"gitlab-plugin@1.5.3\"><gitLabConnection>GitlabViren</gitLabConnection></com.dabsquared.gitlabjenkins.connection.GitLabConnectionProperty><org.jenkinsci.plugins.gitlablogo.GitlabLogoProperty plugin=\"gitlab-logo@1.0.3\"><repositoryName/></org.jenkinsci.plugins.gitlablogo.GitlabLogoProperty></properties><scm class=\"hudson.plugins.git.GitSCM\" plugin=\"git@3.8.0\"><configVersion>2</configVersion><userRemoteConfigs><hudson.plugins.git.UserRemoteConfig><name>origin</name><refspec>+refs/heads/*:refs/remotes/origin/* +refs/merge-requests/*/head:refs/remotes/origin/merge-requests/*</refspec><url>http://10.0.2.15/root/example-groovy.git</url><credentialsId>449eaaad-4ce3-4cd7-88e4-fbda5b6cb318</credentialsId></hudson.plugins.git.UserRemoteConfig></userRemoteConfigs><branches><hudson.plugins.git.BranchSpec><name>*/master</name></hudson.plugins.git.BranchSpec></branches><doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations><browser class=\"hudson.plugins.git.browser.GitLab\"><url>http://10.0.2.15/root/example-groovy</url><version>10.5</version></browser><submoduleCfg class=\"list\"/><extensions/></scm><canRoam>true</canRoam><disabled>false</disabled><blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding><blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding><triggers><com.dabsquared.gitlabjenkins.GitLabPushTrigger plugin=\"gitlab-plugin@1.5.3\"><spec/><triggerOnPush>true</triggerOnPush><triggerOnMergeRequest>true</triggerOnMergeRequest><triggerOnPipelineEvent>false</triggerOnPipelineEvent><triggerOnAcceptedMergeRequest>true</triggerOnAcceptedMergeRequest><triggerOnClosedMergeRequest>true</triggerOnClosedMergeRequest><triggerOnApprovedMergeRequest>true</triggerOnApprovedMergeRequest><triggerOpenMergeRequestOnPush>never</triggerOpenMergeRequestOnPush><triggerOnNoteRequest>true</triggerOnNoteRequest><noteRegex>Jenkins please retry a build</noteRegex><ciSkip>true</ciSkip><skipWorkInProgressMergeRequest>true</skipWorkInProgressMergeRequest><setBuildDescription>true</setBuildDescription><branchFilterType>All</branchFilterType><includeBranchesSpec/><excludeBranchesSpec/><targetBranchRegex/><secretToken>{AQAAABAAAAAQDL6RBgppT1i5trYvfqcHlKjP6RkXr6bgk2JuZHxZvl4=}</secretToken></com.dabsquared.gitlabjenkins.GitLabPushTrigger></triggers><concurrentBuild>false</concurrentBuild><builders><hudson.tasks.Maven><targets>clean install</targets><usePrivateRepository>false</usePrivateRepository><settings class=\"jenkins.mvn.DefaultSettingsProvider\"/><globalSettings class=\"jenkins.mvn.DefaultGlobalSettingsProvider\"/><injectBuildVariables>false</injectBuildVariables></hudson.tasks.Maven><hudson.tasks.Shell><command>export PATH=$PATH:/home/virenmody/Downloads/scitools/bin/linux64/\n" +
                "und\n" +
                "create -db newdb.udb -languages java\n" +
                "add /home/virenmody/ClonedRepos/projectName/\n" +
                "analyze newdb.udb\n" +
                "report newdb.udb</command></hudson.tasks.Shell></builders><publishers><hudson.tasks.junit.JUnitResultArchiver plugin=\"junit@1.24\"><testResults>target/surefire-reports/*.xml</testResults><keepLongStdio>false</keepLongStdio><healthScaleFactor>1.0</healthScaleFactor><allowEmptyResults>true</allowEmptyResults></hudson.tasks.junit.JUnitResultArchiver><hudson.plugins.jacoco.JacocoPublisher plugin=\"jacoco@3.0.1\"><execPattern>**/**.exec</execPattern><classPattern>**/classes</classPattern><sourcePattern>**/src/main/java</sourcePattern><inclusionPattern/><exclusionPattern/><skipCopyOfSrcFiles>false</skipCopyOfSrcFiles><minimumInstructionCoverage>0</minimumInstructionCoverage><minimumBranchCoverage>0</minimumBranchCoverage><minimumComplexityCoverage>0</minimumComplexityCoverage><minimumLineCoverage>0</minimumLineCoverage><minimumMethodCoverage>0</minimumMethodCoverage><minimumClassCoverage>0</minimumClassCoverage><maximumInstructionCoverage>0</maximumInstructionCoverage><maximumBranchCoverage>0</maximumBranchCoverage><maximumComplexityCoverage>0</maximumComplexityCoverage><maximumLineCoverage>0</maximumLineCoverage><maximumMethodCoverage>0</maximumMethodCoverage><maximumClassCoverage>0</maximumClassCoverage><changeBuildStatus>false</changeBuildStatus><deltaInstructionCoverage>0</deltaInstructionCoverage><deltaBranchCoverage>0</deltaBranchCoverage><deltaComplexityCoverage>0</deltaComplexityCoverage><deltaLineCoverage>0</deltaLineCoverage><deltaMethodCoverage>0</deltaMethodCoverage><deltaClassCoverage>0</deltaClassCoverage><buildOverBuild>false</buildOverBuild></hudson.plugins.jacoco.JacocoPublisher><com.dabsquared.gitlabjenkins.publisher.GitLabCommitStatusPublisher plugin=\"gitlab-plugin@1.5.3\"><name>jenkins</name><markUnstableAsSuccess>false</markUnstableAsSuccess></com.dabsquared.gitlabjenkins.publisher.GitLabCommitStatusPublisher></publishers><buildWrappers><hudson.plugins.ws__cleanup.PreBuildCleanup plugin=\"ws-cleanup@0.34\"><deleteDirs>false</deleteDirs><cleanupParameter/><externalDelete/></hudson.plugins.ws__cleanup.PreBuildCleanup><hudson.plugins.timestamper.TimestamperBuildWrapper plugin=\"timestamper@1.8.9\"/></buildWrappers></project>";
        String customJobXml = null;

        String XMLUrlRepositoryName = gitlabHostUrl + "/root/" +projectName;
        String XMLUrlRepositoryGit = gitlabHostUrl + "/root/" +projectName + ".git";

        // TODO Please update the following variables according to your system
        String UNDERSTAND_PATH = "/home/virenmody/Downloads/scitools/bin/linux64/";
        String CLONED_REPOS_PATH = "/home/virenmody/ClonedRepos/" + projectName + "/";
        String understandScript = "export PATH=$PATH:" + UNDERSTAND_PATH + "\n" +
                                    "und create -db project.udb -languages java\n" +
                                    "und add -db project.udb " + CLONED_REPOS_PATH + "\n" +
                                    "und -db project.udb analyze \n" +
                                    "und -db project.udb report";

        // XML parser code from tutorial:
        // https://www.ibm.com/developerworks/library/j-pg05199/index.html
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(jobXml));
            Document doc = db.parse(inputSource);

            //Traversal code from Stack Overflow:
            //  https://stackoverflow.com/questions/5386991/java-most-efficient-method-to-iterate-over-all-elements-in-a-org-w3c-dom-docume
            //  Go through the XML string, print every tag, modify necessary tags
            NodeList nodeList = doc.getElementsByTagName("*");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                //Replace project URL path
                if ((node.getNodeType() == Node.ELEMENT_NODE) && (node.getNodeName() == "url")
                        && (node.getParentNode().getNodeName() == "hudson.plugins.git.UserRemoteConfig")) {

                    node.setTextContent(XMLUrlRepositoryGit);
                }
                //Replace project .git file URL path
                else if ((node.getNodeType() == Node.ELEMENT_NODE) && (node.getNodeName() == "url")
                        && (node.getNextSibling().getNodeName() == "version")) {

                    node.setTextContent(XMLUrlRepositoryName);
                }
                else if ((node.getNodeType() == Node.ELEMENT_NODE) && (node.getNodeName() == "command")) {

                    node.setTextContent(understandScript);
                }
            }

            //Convert Nodelist with modified tags/content to a String
            //Reference: https://codereview.stackexchange.com/questions/106480/converting-partial-xml-node-list-to-a-string

            DOMSource source = new DOMSource();
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            source.setNode(nodeList.item(0));
            transformer.transform(source, result);

            customJobXml = writer.toString();
            //System.out.println("New XML String \n" + customJobXml);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        catch (TransformerException te) {
            te.printStackTrace();
        }

        return customJobXml;
    }

    public static void removeAllGitlabProjects(ProjectApi projectApi) {

        // Delete all Gitlab projects
        try {
            List<Project> projectList = projectApi.getProjects();
            for (Project p : projectList) {
                System.out.println("Deleting Gitlab Project: " + p.getName());
                projectApi.deleteProject(p);
            }
        } catch (GitLabApiException e) {
            System.err.println("Caught Exception for Gitlab Project API: " + e.getMessage());
        }

    }

    public static void removeAllJenkinsJobs(JenkinsServer jenkins) {

        try {
            Map<String, Job> jobsMap = jenkins.getJobs();
            System.out.println("\t - Jobs: " + jobsMap);

            for(Job job : jobsMap.values()) {
                System.out.println("Deleting Jenkins Project: " + job.getName());
                jenkins.deleteJob(job.getName());
            }

        } catch (IOException e) {
            System.err.println("Caught IOException for Jenkins jobs retrieval: " + e.getMessage());
        }

    }

    public static void main(String[] args) {

        //TODO Please update the following for your setup
        String gitlabHostUrl = "http://10.0.2.15";
        String apiAccessToken = "RDtCnzMezmjpih4u6VDm";
        String jenkinsUsername = "admin";
        String jenkinsPassword = "admin";
        //TODO Please update the localhost/ip:port number according to your Jenkins setup (localhost = 10.0.2.15)
        String jenkinsHostUrl = "http://10.0.2.15:8081";

        // Retrieve Gitlab API
        GitLabApi gitLabApi = null;
        try {
            gitLabApi = new GitLabApi(GitLabApi.ApiVersion.V3, gitlabHostUrl, apiAccessToken);
            System.out.println("Gitlab API Instance = " + gitLabApi);
        } catch (Exception e) {
            System.err.println("Caught Exception for Gitlab API Access: " + e.getMessage());
            e.printStackTrace();
        }

        // Retrieve Gitlab Project API
        ProjectApi projectApi = null;
        if (gitLabApi != null) {
            projectApi = gitLabApi.getProjectApi();
        }
        removeAllGitlabProjects(projectApi);


        URI jenkinsUri = null;
        JenkinsServer jenkins = null;

        try {
            jenkinsUri = new URI(jenkinsHostUrl + "/");
            jenkins = new JenkinsServer(jenkinsUri, jenkinsUsername, jenkinsPassword);
        } catch (URISyntaxException e) {
            System.err.println("Caught URISyntaxException for Jenkins Server Retrieval: " + e.getMessage());
        }
        removeAllJenkinsJobs(jenkins);

        GitHubClient client = new GitHubClient();

        //Initialize service to Download code from GitHub
        RepositoryService service = new RepositoryService();
        service.getClient();

        ContentsService contentsService = new ContentsService();

        String query = "maven junit language:java";

        List<SearchRepository> searchRepositoryList = null;

        try {
            searchRepositoryList = service.searchRepositories(query);
        }
        catch (IOException e){
            System.err.println("Caught IOException: " + e.getMessage());
        }

        //Create a Jenkins Build for each repository
        for (SearchRepository searchRepo: searchRepositoryList) {

            //TODO Update following variables based on repositories from Github search
            String projectName = searchRepo.getName();
            String username = searchRepo.getOwner();

            // TODO Please update the LOCAL_PATH variable for your setup
            String repoURL = "https://github.com/" + username + "/" + projectName + ".git";
            String LOCAL_PATH = "/home/virenmody/ClonedRepos/" + projectName;

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

            // Create a new empty project on Gitlab
            Project newProject = null;
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
            // TODO change jenkinsJob to projectName
            String jenkinsJob = projectName;
            //String jenkinsJob = "TestFreestyle";
            String webhookUrl = jenkinsHostUrl + "/project/" + jenkinsJob;
            ProjectHook webhook = null;
            System.out.println("Creating Gitlab to Jenkins webhook: " + webhookUrl);
            try {
                webhook = projectApi.addHook(newProject, webhookUrl, true, false, false);
            } catch (GitLabApiException e) {
                System.err.println("Caught GitlabApiException for Webhook: " + e.getMessage());
            }

            // Create a Jenkins Job for the project
            System.out.println("Creating a Jenkins job for this project: " + jenkinsHostUrl + "/job/" + jenkinsJob);
            try {
                String jobXml = customJenkinsJobXML(gitlabHostUrl, projectName);
                jenkins.createJob(projectName, jobXml);
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
        }

    }
}
