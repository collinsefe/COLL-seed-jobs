def githubUrl = 'https://github.com/collinsefe/spring-boot-react-example.git'
def gitCreds = "prodigital-collinsefe"


def environments = [
    demo: [
        name: "Demo",
        branch: "demo",
        ec2_instance: "ec2-instance-dev",
        script_path: "resources/DEMO/run_backend.sh",
        port: "8081"
    ],
    test: [
        name: "Testing",
        branch: "test",
    ],
]

environments.each { env, config ->
    job("CAP-BACKEND-${env.toUpperCase()}-JOB") {
        description("Job to deploy the application to the ${config.name} environment.")
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

    disabled(false)
    concurrentBuild(false)


         properties {
        disableConcurrentBuilds()
    }

        publishers {
            downstream("JobName", "SUCCESS")
        }
    }
}
