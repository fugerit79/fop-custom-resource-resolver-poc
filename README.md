# Different behaviour for Fop Custom Resource Resolver in FOP 2.6 and 2.7+ [Proof of concept]

## Abstract
For a library project I've been working, I designed a custom ResourceResolver to load fonts from the class loader instead than from the file system (the default behaviour of FOP).  
This library was originally developed linking Apache FOP 2.6.  
Recently I wanted to upgrade to Apache FOP 2.7 or 2.8, but when linking the new version of FOP, it seems the custom ResourceResolver is ignored.  
This project provide a simple Proof of concept of this different behaviour.  
The purpose is to open a bug on Apche FOP JIRA bug track and attach this POC as sample code.  

## Quickstart
* mvn clean install -P fop26	[Test OK]  
* mvn clean install -P fop27  [Test Failed]  
* mvn clean install -P fop28  [Test Failed]  
The link to Apache FOP is achivied using a property fop-version overriden in thre different profiles : 
* fop26 -> version 2.6  
* fop27 -> version 2.7  
* fop28 -> version 2.8  
As you can see in the [pom.xml](pom.xml) file

## The project structure
This project consists of basically of : 
* [ClassLoaderResourceResolver](src/main/java/org/fugerit/java/poc/fop/custom/resource/resolver/ClassLoaderResourceResolver.java) - A custom ResourceResolver loading from the classloader uri starting with "classpath://"  
* [SampleDocumentGenerator](src/main/java/org/fugerit/java/poc/fop/custom/resource/resolver/SampleDocumentGenerator.java) - A sample code customized from the [sample code on fop site](https://xmlgraphics.apache.org/fop/2.8/embedding.html) to use the previous custom ResourceResolver.  
* A resource folder with the fonts to be loaded : *src/main/resources/font*
* The Apache FOP configuration file [fop-sample-config.xml](src/fop/fop-sample-config.xml)  
* The Apache FOP sample .fo [fop-sample-doc.fo](src/fop/fop-sample-doc.fo)  
* A JUnit [TestDocGeneration](src/test/java/test/TestDocGeneration.java) to test the document generation.  
* A [pom.xml](pom.xml) file with three profiles for launching the JUnit with Apache FOP 2.6 (fop26), 2.7 (fop27) and 2.9 (fop28).  

## Test done
This porject had been tested with the same behaviour with : 
* JDK 1.8.0_333 and JDK 11.0.16
* Apache Maven 3.8.6

**Some sample build logs are provided :**  
1. [mvn clean install -P fop26 >> sample_build_log/build_log_profile_fop26_jdk8_win.txt 2>&1](sample_build_log/build_log_profile_fop26_jdk8_win.txt)  
2. [mvn clean install -P fop27 >> sample_build_log/build_log_profile_fop27_jdk8_win.txt 2>&1](sample_build_log/build_log_profile_fop27_jdk8_win.txt)  
3. [mvn clean install -P fop28 >> sample_build_log/build_log_profile_fop28_jdk8_win.txt 2>&1](sample_build_log/build_log_profile_fop28_jdk8_win.txt)  
4. [mvn clean install -X -P fop26 >> sample_build_log/build_log_profile_fop26_jdk8_win_debug.txt 2>&1](sample_build_log/build_log_profile_fop26_jdk8_win_debug.txt)  
5. [mvn clean install -X -P fop27 >> sample_build_log/build_log_profile_fop27_jdk8_win_debug.txt 2>&1](sample_build_log/build_log_profile_fop27_jdk8_win_debug.txt)  
6. [mvn clean install -X -P fop28 >> sample_build_log/build_log_profile_fop28_jdk8_win_debug.txt 2>&1](sample_build_log/build_log_profile_fop28_jdk8_win_debug.txt)  
7. [mvn clean install -P fop26 >> sample_build_log/build_log_profile_fop26_jdk11_win.txt 2>&1](sample_build_log/build_log_profile_fop26_jdk11_win.txt)  
8. [mvn clean install -P fop27 >> sample_build_log/build_log_profile_fop27_jdk11_win.txt 2>&1](sample_build_log/build_log_profile_fop27_jdk11_win.txt)  
9. [mvn clean install -P fop28 >> sample_build_log/build_log_profile_fop28_jdk11_win.txt 2>&1](sample_build_log/build_log_profile_fop28_jdk11_win.txt)
10. [mvn clean install -X -P fop26 >> sample_build_log/build_log_profile_fop26_jdk11_win_debug.txt 2>&1](sample_build_log/build_log_profile_fop26_jdk11_win_debug.txt)  
11. [mvn clean install -X -P fop27 >> sample_build_log/build_log_profile_fop27_jdk11_win_debug.txt 2>&1](sample_build_log/build_log_profile_fop27_jdk11_win_debug.txt)  
12. [mvn clean install -X -P fop28 >> sample_build_log/build_log_profile_fop28_jdk11_win_debug.txt 2>&1](sample_build_log/build_log_profile_fop28_jdk11_win_debug.txt)   
13. [mvn clean install -P fop26 >> sample_build_log/build_log_profile_fop26_jdk8_linux.txt 2>&1](sample_build_log/build_log_profile_fop26_jdk8_linux.txt)  
14. [mvn clean install -P fop27 >> sample_build_log/build_log_profile_fop27_jdk8_linux.txt 2>&1](sample_build_log/build_log_profile_fop27_jdk8_linux.txt)  
15. [mvn clean install -P fop28 >> sample_build_log/build_log_profile_fop28_jdk8_linux.txt 2>&1](sample_build_log/build_log_profile_fop28_jdk8_linux.txt)
16. [mvn clean install -X -P fop26 >> sample_build_log/build_log_profile_fop26_jdk8_linux_debug.txt 2>&1](sample_build_log/build_log_profile_fop26_jdk8_linux_debug.txt)  
17. [mvn clean install -X -P fop27 >> sample_build_log/build_log_profile_fop27_jdk8_linux_debug.txt 2>&1](sample_build_log/build_log_profile_fop27_jdk8_linux_debug.txt)  
18. [mvn clean install -X -P fop28 >> sample_build_log/build_log_profile_fop28_jdk8_linux_debug.txt 2>&1](sample_build_log/build_log_profile_fop28_jdk8_linux_debug.txt)  
19. [mvn clean install -P fop26 >> sample_build_log/build_log_profile_fop26_jdk11_linux.txt 2>&1](sample_build_log/build_log_profile_fop26_jdk11_linux.txt)  
20. [mvn clean install -P fop27 >> sample_build_log/build_log_profile_fop27_jdk11_linux.txt 2>&1](sample_build_log/build_log_profile_fop27_jdk11_linux.txt)  
21. [mvn clean install -P fop28 >> sample_build_log/build_log_profile_fop28_jdk11_linux.txt 2>&1](sample_build_log/build_log_profile_fop28_jdk11_linux.txt)
22. [mvn clean install -X -P fop26 >> sample_build_log/build_log_profile_fop26_jdk11_linux_debug.txt 2>&1](sample_build_log/build_log_profile_fop26_jdk11_linux_debug.txt)  
23. [mvn clean install -X -P fop27 >> sample_build_log/build_log_profile_fop27_jdk11_linux_debug.txt 2>&1](sample_build_log/build_log_profile_fop27_jdk11_linux_debug.txt)  
24. [mvn clean install -X -P fop28 >> sample_build_log/build_log_profile_fop28_jdk11_linux_debug.txt 2>&1](sample_build_log/build_log_profile_fop28_jdk11_linux_debug.txt)  

As can be noted, in the build linked to Apache FOP 2.6 is possible to read the log of the custom ResourceResolver : 

```
2022-11-20 18:29:15.358 [main] INFO  org.fugerit.java.poc.fop.custom.resource.resolver.ClassLoaderResourceResolver - TestClassLoaderResourceResolver.getResource() -> file:/C:/workspace/git/fop-custom-resource-resolver-poc/./target/classes/font/TitilliumWeb-Bold.ttf
2022-11-20 18:29:15.477 [main] INFO  org.fugerit.java.poc.fop.custom.resource.resolver.ClassLoaderResourceResolver - TestClassLoaderResourceResolver.getResource() -> file:/C:/workspace/git/fop-custom-resource-resolver-poc/./target/classes/font/TitilliumWeb-BoldItalic.ttf
2022-11-20 18:29:15.492 [main] INFO  org.fugerit.java.poc.fop.custom.resource.resolver.ClassLoaderResourceResolver - TestClassLoaderResourceResolver.getResource() -> file:/C:/workspace/git/fop-custom-resource-resolver-poc/./target/classes/font/TitilliumWeb-Italic.ttf
2022-11-20 18:29:15.503 [main] INFO  org.fugerit.java.poc.fop.custom.resource.resolver.ClassLoaderResourceResolver - TestClassLoaderResourceResolver.getResource() -> file:/C:/workspace/git/fop-custom-resource-resolver-poc/./target/classes/font/TitilliumWeb-Regular.ttf
2022-11-20 18:29:16.512 [main] INFO  org.fugerit.java.poc.fop.custom.resource.resolver.ClassLoaderResourceResolver - TestClassLoaderResourceResolver.getResource() -> classpath://font/TitilliumWeb-Regular.ttf
```

The same lines are not present when using the profiles linking to Apache FOP 2.7 and 2.8.  

PS: the original library project where this BUG was found is [Fugerit Venus](https://github.com/fugerit-org/fj-doc).  
