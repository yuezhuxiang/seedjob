package utilities

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy 
import javaposse.jobdsl.dsl.DslFactory

@Builder(builderStrategy = SimpleStrategy, prefix = '')
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