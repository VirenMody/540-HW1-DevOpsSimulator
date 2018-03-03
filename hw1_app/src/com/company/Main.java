package com.company;


import org.eclipse.egit.github.core.*;
import org.eclipse.egit.github.core.service.*;
import com.google.gson.*;
import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Hello.");

        // Download code from GitHub
        RepositoryService service = new RepositoryService();

        List<Repository> repositoryList = null;
        try {
            repositoryList = service.getRepositories("guillermokrh");
        }
        catch (IOException e){
            System.err.println("Caught IOException: " + e.getMessage());
        }
        for (Repository repo : repositoryList)
            System.out.println(repo.getName() + " Watchers: " + repo.getWatchers());
    }
}
