This is an attempt at figuring out why test-compile takes so long for the jpa kotlin plugin.
https://stackoverflow.com/questions/63678413/kotlin-1-4-0-jpa-plugin-kotlin-maven-noarg-never-finishes-on-test-compile-but

```shell script
mvn clean test
```

almost all of the build time is
[INFO] --- kotlin-maven-plugin:1.4.10:test-compile (test-compile) @ FooBar ---
[INFO] Applied plugin: 'jpa'
