package utils

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job
import javaposse.jobdsl.dsl.jobs.MultibranchWorkflowJob

@Builder(builderStrategy = SimpleStrategy, prefix = '')
class BtcGithubPipeline {
    String viewPrefix
    String projectKey
    String repository
    String jobSuffix = ""
    String includes = "*"
    String jenkinsfileLocation = "Jenkinsfile"
    Boolean discoverBranches = true
    Boolean discoverTags = true

    MultibranchWorkflowJob build(DslFactory dslFactory) {
        def cleanedRepoString = this.repository.toLowerCase().replaceAll(/_/, '-');

        def jobId = "${this.viewPrefix.toLowerCase()}-${cleanedRepoString}"
        if (this.jobSuffix) jobId += "-${jobSuffix}"

        dslFactory.multibranchPipelineJob(jobId) {
            branchSources {
                github {
                    id(jobId)
                    includes(this.includes)
                    repoOwner(this.projectKey)
                    repository(this.repository)
                    scanCredentialsId('0f57727d-e2bf-42ef-bdc5-94a53d7248c1')
                    checkoutCredentialsId('0f57727d-e2bf-42ef-bdc5-94a53d7248c1')
                }
            }
            factory {
                workflowBranchProjectFactory {
                    scriptPath(this.jenkinsfileLocation)
                }
            }
            configure {
                def traits = it / sources / data / 'jenkins.branch.BranchSource' / source / traits
                if (this.discoverBranches) {
                    traits << 'org.jenkinsci.plugins.github_branch_source.BranchDiscoveryTrait' {
                        strategyId(3) // detect all branches -refer the plugin source code for various options
                    }
                }
                if (this.discoverTags) {
                    traits << 'org.jenkinsci.plugins.github_branch_source.TagDiscoveryTrait' { }
                }
            }
            orphanedItemStrategy {
                discardOldItems {
                    daysToKeep(20)
                    numToKeep(25)
                }
            }
        }

    }
}
