def githubUrl = 'https://github.com'
def gitCreds = "prodigital-collinsefe"


def environments = [
    dev: [
        name: "Demo",
        branch: "demo",
        ec2_instance: "ec2-instance-dev",
        script_path: "resources/DEV/run_backend.sh",
        port: "8081"
    // ],
    // test: [
    //     name: "Testing",
    //     branch: "test",
    //     ec2_instance: "ec2-instance-staging",
    //     script_path: "resources/TEST/run_backend.sh",
    //     port: "8082"
    // ],
]

environments.each { env, config ->
    job("CAP-BACKEND-${env.toUpperCase()}-JOB") {
        description("Job to deploy the application to the ${config.name} environment.")
        keepDependencies(false)

        multiscm {
            git {
                remote {
                    name('origin')
                    url("https://github.com/collinsefe/spring-boot-react-app.git")
                    credentials("prodigital-collinsefe")
                }
                extensions {
                    branch("*/${config.branch}")
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
