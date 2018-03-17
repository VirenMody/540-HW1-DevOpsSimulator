Configuration Instructions
--- 
Please follow these steps:

### Machine/Environment

+ VirtualBox Virtual Machine
+ 8 GB RAM
+ Ubuntu 16.04 64-Bit
+ Virtual Disk created on setup 50GB (VDI - Virtual Disk Image)
+ General Settings > Advanced > Bidirectional for both sharing clipboard and drag and drop
+ System Settings > Processor > 4 Cores
+ System Settings > Acceleration > Enable VT-x/AMD-V, Nested Paging, 
+ Display Settings > Video Memory >  128mb and Enable 3D Acceleration

### Initial Setup/ Configuration

* Install Oracle JDK 8 (NOT 9)
* Install IntelliJ
* **Install Gitlab**
  * Local installation, not with docker, please follow this [**Instruction Link**](https://about.gitlab.com/installation/#ubuntu).
  * Set `EXTERNAL_URL="http://10.0.2.15"` during install. This is the IP for our virtual machine.
  * Default `username: root`
  * `Password: rootroot`
* **Install Jenkins**
  * Local installation, not with docker, please follow this [**Instruction Link**](https://jenkins.io/doc/book/installing/#debian-ubuntu)
  * Gitlab is running on `http://10.0.2.15` so Jenkins needs to run on a different port (`i.e. http://10.0.2.15:8081`)
  * Stop Jenkins server: `sudo service jenkins stop`
   Change `HTTP_PORT=8080` to `HTTP_PORT=8081` in `/etc/default/jenkins`
  * Start Jenkins server: sudo service jenkins start
  * please Follow rest of the tutorial to unlock Jenkins, please install suggested plugins
  * Create First Admin User: `Username: admin` `Password: admin`
* Configure Jenkins
  * Manage Jenkins > Plugins <br/>
    Gitlab <br/>
    Gitlab Hook <br/>
    JaCoCo <br/>
    JUnit <br/>
    Test In Progress <br/>
    Workspace Cleanup <br/>
  * Manage Jenkins > Configure Global Security 
    Home Directory should be `/var/lib/jenkins` <br/>
    Jenkins URL should be `http://10.0.2.15:8081` <br/>
    Uncheck `Enable security` <br/>
    In section CSRF Protection, Uncheck “Prevent Cross Site Request Forgery Exploits" <br/>




Understand
---
Please follow the following steps:
1. Download the software at [Download](https://scitools.com/download-2/), unzip the folder, add the path by editing the .bashrc file.
   + through home `gedit .bashrc`
   + To the bottom of the file, add line `export PATH=$PATH:/location of the unzipped scitools folder/bin/linux64` example: export PATH=$PATH:/home/dhananjay/Downloads/scitools/bin/linux64
   + run Understand: `$ understand`and make sure to enter the license key and see if it is up and running.

2. Update the following variables in the `customJenkinsJobXML()` fucntion in the source code
   + `UNDERSTAND_PATH`: to the PATH as on your machine. 
example : /home/dhananjay/Downloads/scitools/bin/linux64 
   + `CLONED_REPOS_PATH`: to the path where you will be storing the downloaded repos on your local machine. Example: /home/dhananjay/ClonedRepos/ 

#### Analysis Report
The analysis report is being generated at the particular job's folder under the jenkins workspace folder as a .txt and html files on your local machine. Kidnly, visit the folders to access the reports.
Example: under the /var/lib/jenkins/workspace/"JobName". The job name is the name of the Repository being analyzed.
