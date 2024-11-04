def environments = [
    dev: [
        name: "Development",
        branch: "dev",
        ec2_instance: "ec2-instance-dev",
        script_path: "resources/DEV/run_dev.sh"
    ],
    staging: [
        name: "Testing",
        branch: "test",
        ec2_instance: "ec2-instance-staging",
        script_path: "resources/TEST/run_test.sh"
    ],
    prod: [
        name: "Production",
        branch: "main",
        ec2_instance: "ec2-instance-prod",
        script_path: "resources/PROD/run_prod.sh"
    ]
]

environments.each { env, config ->
    job("COLL_${env.toUpperCase()}-Create-Job") {
        description("Job to deploy the application to the ${config.name} environment.")
        keepDependencies(false)

        multiscm {
            git {
                remote {
                    name('origin')
                    url("https://github.com/collinsefe/capgem-app.git")
                    credentials("12ac599-aee0")
                }
                extensions {
                    branch("*/${config.branch}")
                }
            }
        }

        // label('A-Build-Node')

        disabled(false)
        concurrentBuild(false)

        steps {
            shell(readFileFromWorkspace(config.script_path))
            shell("echo Deploying to EC2 instance: ${config.ec2_instance}")
            // SSH command to deploy to specific EC2 instance
            shell("ssh -i /tmp/collinsefe.pem ec2-user@${config.ec2_instance} 'bash -s' < ./run_backend.sh")
        }

        publishers {
            downstream("JobName", "SUCCESS")
        }
    }
}
