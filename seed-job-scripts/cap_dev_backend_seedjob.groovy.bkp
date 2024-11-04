String env = 'dev'

def githubUrl = 'https://github.com'
def gitCreds = "prodigital-collinsefe"
// Define environments and related parameters
def environments = [
    'dev': [
        branch: 'dev',
        ec2Instance: 'ec2-instance-dev'
    ],
    'staging': [
        branch: 'staging',
        ec2Instance: 'ec2-instance-staging'
    ],
    'prod': [
        branch: 'main',
        ec2Instance: 'ec2-instance-prod'
    ]
]
// Create jobs for each environment
environments.each { env, params ->

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
    
 // Pass in the environment-specific script and EC2 instance
        steps {
            shell(readFileFromWorkspace("resources/${env.toUpperCase()}/helloJenkins.sh"))
            shell("echo Deploying to EC2 instance: ${params.ec2Instance}")
        }

        publishers {
            downstream("JobName", "SUCCESS")
            triggers {
                downstream("zdb-addReadANDWrite", "SUCCESS")
            }
        }
        wrappers {
            credentialsBinding {
                string("masterpass", "asdjhafh123")
                file("PE_PBE_FILE", "32743646hjsdjhdjgdggds")
            }
            timestamps()
        }
        configure {
            it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
                'autoRebuild'('false')
                'rebuildDisabled'('false')
            }
        }
    }
}