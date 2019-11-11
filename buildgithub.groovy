package utilities

import javaposse.jobdsl.dsl.DslFactory

class GithubMultibranch {
 
    String name
    String description
    String displayName
    String repositoryOwner
    String repositoryName
    String credentials
    String includeBranches
    String excludeBranches
 
 
    void build(DslFactory dslFactory) {
        def job = dslFactory.multibranchPipelineJob(name) {
            description(description)
            displayName(displayName)
            branchSources {
                github {
                    scanCredentialsId(credentials)
                    repoOwner(repositoryOwner)
                    repository(repositoryName)
                    includes(includeBranches)
                    excludes(excludeBranches)
                }
            }
            factory {
                workflowBranchProjectFactory {
                    scriptPath('.jenkins/Jenkinsfile')
                }
            }
            orphanedItemStrategy {
                discardOldItems {
                    numToKeep(15)
                }
            }
        }
    }
}

def multiPipeline = new GithubMultibranch(
    description: 'Just try make world better',
    name: 'Github-Test',
    displayName: 'Github-Test',
    repositoryOwner: "yuezhuxiang",
    repositoryName: "jenkinsci-example",
    credentials: '0f57727d-e2bf-42ef-bdc5-94a53d7248c1',
    includeBranches: 'development master branchA',
    excludeBranches: ''
).build(this)