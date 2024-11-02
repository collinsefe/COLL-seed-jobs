String env = 'dev'

def githubUrl = 'https://github.com'
def gitCreds = "prodigital-collinsefe"

job("CAP_${env.toUpperCase()}-Job"){
    description("This Job is used to create the Node Server and its versioned. Changes should be made through the repo")
    keepDependencies(false)

    multiscm{
        git{
            remote{
                name('origin')
                url ("https://gitlab.com/collinsefe/spring-boot-react-example")
                credentials(gitCreds)
                
            }
            extensions{
                branch('*/main')
            }
        }
    }

    // label('A-Build-Node')

    disabled(false)
    concurrentBuild(false)

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

        pipeline {
            script {
                load "resources/${env.toUpperCase()}/Jenkinsfile"
            }
        }
    }

    publishers{
        downstream("JobName", "SUCCESS")
        triggers{
            downstream("zdb-addReadANDWrite", "SUCCESS")
        }
    }

    configure{
        it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
            'autoRebuild'('false')
            'rebuildDisabled'('false')
        }
    }
}
