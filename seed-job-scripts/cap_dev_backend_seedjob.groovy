String env = 'dev'

def githubUrl = 'https://github.com'
def gitCreds = "prodigital-collinsefe"

job("CAP-BACKEND-${env.toUpperCase()}-Job") {
    description("This Job is used to create the Node Server and its versioned. Changes should be made through the repo")
    keepDependencies(false)

    scm {
        git {
            remote {
                name('origin')
                url ("https://github.com/collinsefe/spring-boot-react-example.git")
                credentials(gitCreds)
            }
            extensions {
                branch('*/dev')
            }
        }
    }

    disabled(false)
    concurrentBuild(false)

       steps {
        // Load and execute the Jenkinsfile as a shell script
        scriptPath("resources/${env.toUpperCase()}/Jenkinsfile")
        // shell(readFileFromWorkspace("resources/${env.toUpperCase()}/run_backend.sh"))
    }


    publishers {
        downstream("CAP-DOCKER-DEV-Job", "SUCCESS")
        triggers {
            downstream("zdb-addReadANDWrite", "SUCCESS")
        }
    }

    // configure {
    //     it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
    //         'autoRebuild'('false')
    //         'rebuildDisabled'('false')
    //     }
    // }

    
}
