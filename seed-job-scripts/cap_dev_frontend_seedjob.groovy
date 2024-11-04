String env = 'dev'

def githubUrl = 'https://github.com'
def gitCreds = "prodigital-collinsefe"

job("COLL-FRONTEND-${env.toUpperCase()}-Job") {
    description("This Job is used to create the Node Server and its versioned. Changes should be made through the repo")
    keepDependencies(false)

    scm{
        git{
            remote{
                name('origin')
                url ("https://gitlab.com/collinsefe/cloud-assignment.git")
                credentials(gitCreds)
            }
            extensions{
                branch('*/main')
            }
        }
    }

    disabled(false)
    concurrentBuild(false)

       steps {
        // Load and execute the Jenkinsfile as a shell script
        shell(readFileFromWorkspace("resources/${env.toUpperCase()}/run_frontend.sh"))
    }
}
