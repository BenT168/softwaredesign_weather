mvn install:install-file -Dfile=lib/weather.jar -DgroupId=com.weather -DartifactId=weather -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true
mvn clean test cobertura:cobertura cobertura:check checkstyle:checkstyle checkstyle:check
