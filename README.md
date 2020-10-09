This is an attempt at figuring out why test-compile takes so long for the jpa kotlin plugin.
https://stackoverflow.com/questions/63678413/kotlin-1-4-0-jpa-plugin-kotlin-maven-noarg-never-finishes-on-test-compile-but

Reported in this issue:
https://youtrack.jetbrains.com/issue/KT-41965

```shell script
mvn clean test
```

Almost all of the build time is
```
[INFO] --- kotlin-maven-plugin:1.4.10:test-compile (test-compile) @ FooBar ---
[INFO] Applied plugin: 'jpa'
```

There are 2 issues here:
 1. The Kotlin compiler and IntelliJ IDEA plugin are slow on *uses* of [CompareToContract.testCompareTo()](src/test/java/yoyodyne/CompareToContract.java)

     a. I've provided a fix for my code.
     The type signature is unnecessarily confusing and lenient.
     Kotlin digests the corrected signature without problem.

     b. Kotlin should not slow down so much over this.
     Automatically converting the code to Kotlin in IntelliJ ends up suggesting the correct simpler signature, so that code does the right thing.

     c. If Kotlin is slow, it should cache whatever it decides so that it doesn't slow down again each time it needs this method again.
     Whatever part of Kotlin gets hung up on this looks single-threaded already.

 2. kotlin-maven-noarg writes out `Applied plugin: 'jpa'` which makes it look like that's what's slow.
    It's not.  Consider either clarifying with `Completed applying plugin: 'jpa'` or two messages like
    `Applying plugin 'jpa'` and `'jpa' done`.