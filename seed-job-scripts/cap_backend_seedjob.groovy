def githubUrl = 'https://github.com'
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
    job("CAP-BACKEND-${env.toUpperCase()}-JOB") {
        description("Job to deploy the application to the ${config.name} environment.")
        keepDependencies(false)

        multiscm {
            git {
                remote {
                    name('origin')
                    url("https://gitlab.com/collinsefe/spring-boot-react-example.git")
                    credentials("prodigital-collinsefe")
                }
                extensions {
                    branch("*/${config.branch}")
                }
            }
        }

    disabled(false)
    concurrentBuild(false)

        // steps {
        //     shell(readFileFromWorkspace("resources/${env.toUpperCase()}/run_backend.sh"))
        //     // shell("echo Deploying to EC2 instance: ${params.ec2Instance}")
        // }

        // label('A-Build-Node')

         properties {
        disableConcurrentBuilds()
    }

        publishers {
            downstream("JobName", "SUCCESS")
        }
    }
}
