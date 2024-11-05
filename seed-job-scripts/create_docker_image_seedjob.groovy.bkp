String env = 'dev'

def githubUrl = 'https://github.com'
def gitCreds = "prodigital-collinsefe"

job("CREATE-DOCKER-IMAGE-${env.toUpperCase()}-Job"){
    description("This Job is used to create the Node Server and its versioned. Changes should be made through the repo")
    keepDependencies(false)

    scm{
        git{
            remote{
                name('origin')
                url ("https://github.com/collinsefe/dotnet-app-image.git")
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

    steps{
        shell("groovy resources/${env.toUpperCase()}/run_docker.groovy")
        // shell(readFileFromWorkspace("resources/${env.toUpperCase()}/run_docker.groovy"))
    }

    publishers{
        downstream("CAP-FRONTEND-${env.toUpperCase()}-Job", "SUCCESS")
        triggers{
            downstream("zdb-addReadANDWrite", "SUCCESS")
        }
    }
}
