String env = 'dev'

def githubUrl = 'https://github.com'
def gitCreds = "prodigital-collinsefe"

job("CAP_${env.toUpperCase()}-Job") {
    description("This Job is used to create the Node Server and its versioned. Changes should be made through the repo")
    keepDependencies(false)

    multiscm {
        git {
            remote {
                name('origin')
                url ("https://gitlab.com/cloud-devops-assignments/spring-boot-react-example")
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
        // Using the pipeline DSL to load the Jenkinsfile
        script {
            // Assuming the Jenkinsfile is in the resources folder
            def jenkinsFilePath = "resources/${env.toUpperCase()}/Jenkinsfile"
            load jenkinsFilePath
        }
    }

    publishers {
        downstream("JobName", "SUCCESS")
        triggers {
            downstream("zdb-addReadANDWrite", "SUCCESS")
        }
    }

    configure {
        it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
            'autoRebuild'('false')
            'rebuildDisabled'('false')
        }
    }
}
