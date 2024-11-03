String env = 'test'

def githubUrl = 'https://github.com'
def gitCreds = "prodigital-collinsefe"

job("CAP-BACKEND-${env.toUpperCase()}-Job"){
    description("This Job is used to create the Node Server and its versioned. Changes should be made through the repo")
    keepDependencies(false)

    multiscm{
        git{
            remote{
                name('origin')
                url ("https://gitlab.com/cloud-devops-assignments/spring-boot-react-example.git")
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
        shell(readFileFromWorkspace("resources/${env.toUpperCase()}/run_test.sh"))
    }

    publishers{
        downstream("JobName", "SUCCESS")
        triggers{
            downstream("zdb-addReadANDWrite", "SUCCESS")
        }
    }
    // wrappers{
    //     credentialsBinding{
    //         string("masterpass", "asdjhafh123")
    //         file("PE_PBE_FILE", "32743646hjsdjhdjgdggds")
    //     }
    //     timestamps()
    // }
    configure{
        it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
            'autoRebuild'('false')
            'rebuildDisabled'('false')
        }
    }
}
