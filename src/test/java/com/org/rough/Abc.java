package com.org.rough;

import java.io.File;

public class Abc {
	public static void main(String[] args) {
		   File f = new File("D://tutorialData/java");
	        String parent = f.getParent();
	        System.out.println("Name of parent dir : "+parent);
	}

}
