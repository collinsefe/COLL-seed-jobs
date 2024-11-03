String env = 'dev'

def githubUrl = 'https://github.com'
def gitCreds = "prodigital-collinsefe"

job("CAP-DOCKER-${env.toUpperCase()}-Job"){
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
        shell(readFileFromWorkspace("resources/${env.toUpperCase()}/run_docker.sh"))
    }

    publishers{
        downstream("JobName", "SUCCESS")
        triggers{
            downstream("zdb-addReadANDWrite", "SUCCESS")
        }
    }
}
