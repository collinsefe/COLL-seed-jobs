# JENKINS DSL JOB

This repository contains Jenkins seed job scripts to automate the creation and management of Jenkins pipeline jobs for multiple applications in different environments. 

The seed jobs dynamically create jobs for the Development and Testing environments based on predefined configurations for each project.

## Repository Overview
These seed jobs are designed to:

Set up Jenkins pipeline jobs to build, test, and deploy frontend and backend applications.

Trigger on GitHub repository changes, ensuring continuous integration for both applications.

## GitHub Repository URLs
CAP-FRONTEND: https://github.com/collinsefe/dotnet-app-image

CAP-BACKEND: https://github.com/collinsefe/spring-boot-react-app

## Jenkins Git Credentials ID

Both seed jobs require the Jenkins Git credential ID <your-credentials-go-here>  to authenticate and fetch code from the repositories.

## Environment Configurations
The environments are defined in a map, environments, where each entry specifies:

**name:** The friendly name of the environment.

**branch:** The Git branch associated with the environment.

## Supported Environments:
Development (dev branch)

Testing (test branch)

Production - (main branch) - WIP

### Job Structure
For each environment, these seed jobs create a corresponding Jenkins pipeline job with the naming format:

CAP-FRONTEND-<ENVIRONMENT>-Job (for the frontend application)

CAP-BACKEND-<ENVIRONMENT>-Job (for the backend application)

### Each generated job has the following features:

**Description:** Details the purpose of the job and advises making changes via the repository.

**SCM Configuration:** Configures each job to pull the appropriate branch from the designated GitHub repository.

**Trigger:** Automatically triggers a build on GitHub push events to the specified branch.

**Build Properties:** Disables concurrent builds to prevent overlapping executions.

### Seed Job Scripts

CAP-FRONTEND Seed Job Script

The frontend seed job script creates jobs to build and deploy the CAP-FRONTEND application. 

Here’s the script:

```groovy
def githubUrl = 'https://github.com/collinsefe/dotnet-app-image.git'

def gitCreds = 'prodigital-collinsefe'

def environments = [
    dev: [
        name: 'Development',
        branch: 'dev'
    ],
    test: [
        name: 'Testing',
        branch: 'test'
    ],
]

environments.each { env, config ->
    pipelineJob("CAP-FRONTEND-${env.toUpperCase()}-Job") {
        description('This Job is used to create the Node Server and is versioned. Changes should be made through the repo.')
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
```
### CAP-BACKEND Seed Job Script

The backend seed job script creates jobs to build and deploy the CAP-BACKEND application. 

Here’s the script:

``` groovy 

def githubUrl = "https://github.com/collinsefe/spring-boot-react-app.git"
def gitCreds = 'prodigital-collinsefe'

def environments = [
    dev: [
        name: 'Development',
        branch: 'dev'
    ],
    test: [
        name: 'Testing',
        branch: 'test'
    ],
]

environments.each { env, config ->
    pipelineJob("CAP-BACKEND-${env.toUpperCase()}-Job") {
        description('This Job is used to create the Node Server and is versioned. Changes should be made through the repo.')
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
```

Usage

### Set Up GitHub Credentials: 

Ensure Jenkins has a credential entry with the ID <your-credentials-id-goes-here> to access the GitHub repositories.

### Configure GitHub Webhooks: 

Set up webhooks on both repositories to notify Jenkins on push events.

### Run the Seed Jobs: 

Execute these scripts in Jenkins to create environment-specific jobs for the CAP-FRONTEND and CAP-BACKEND applications.

### License

This repository and seed job code are licensed under the MIT License.
