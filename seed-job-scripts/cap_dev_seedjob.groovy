String env = 'dev'

def githubUrl = 'https://github.com'
def gitCreds = "prodigital-collinsefe"

job("CAP_${env.toUpperCase()}-Job") {
    description("This Job is used to create the Node Server and its versioned. Changes should be made through the repo")
    keepDependencies(false)

    scm {
        git {
            remote {
                name('origin')
                url ("https://gitlab.com/cloud-devops-assignments/spring-boot-react-example.git")
                credentials(gitCreds)
            }
            extensions {
                branch('*/main')
            }
        }
    }

    disabled(false)
    concurrentBuild(false)

       steps {
        // Load and execute the Jenkinsfile as a shell script
        shell(readFileFromWorkspace("resources/${env.toUpperCase()}/run_pipeline.sh"))
    }


    // publishers {
    //     downstream("JobName", "SUCCESS")
    //     triggers {
    //         downstream("zdb-addReadANDWrite", "SUCCESS")
    //     }
    // }

    // configure {
    //     it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
    //         'autoRebuild'('false')
    //         'rebuildDisabled'('false')
    //     }
    // }
}
