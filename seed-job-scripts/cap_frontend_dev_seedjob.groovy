def githubUrl = 'https://github.com/collinsefe/dotnet-app-image.git'
def gitCreds = "prodigital-collinsefe"


def environments = [
    dev: [
        name: "Development",
        branch: "dev",
        ec2_instance: "ec2-instance-dev",
        script_path: "resources/DEV/run_backend.sh",
        port: "8081"
    ],
    test: [
        name: "Testing",
        branch: "test",
        ec2_instance: "ec2-instance-staging",
        script_path: "resources/TEST/run_backend.sh",
        port: "8082"
    ],
]

environments.each { env, config ->
pipelineJob("CAP-FRONTEND-${env.toUpperCase()}-Job") {
    description("This Job is used to create the Node Server and is versioned. Changes should be made through the repo.")
    keepDependencies(false)

    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url(githubUrl)
                        credentials(gitCreds)
                    }
                    branch("*/${config.branch}")
                }
            }
        }
    }
    triggers {
            githubPush()  
        }

    properties {
        disableConcurrentBuilds()
    }
 }
}
