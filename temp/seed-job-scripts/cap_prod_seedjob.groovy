String env = 'pe'

def githubUrl = 'https://github.com'
def gitCreds = "d447df7b-7f19-4a5e-9939-fd0e58b8f1fb"

job("IHC_${env}-Create-Job"){
    description("This Job is used to create the Node Server and its versioned. Changes should be made through the repo")
    keepDependencies(false)

    multiscm{
        git{
            remote{
                name('origin')
                url ("https://github.com/collinsefe/capgem-app")
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
        shell(readFileFromWorkspace("resources/${env.toUpperCase()}/run.sh"))
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
