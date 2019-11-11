
def gitUrl = "https://github.com/yuezhuxiang/jenkinsci-example.git"

job("MyProject-Build") {
    description "Builds MyProject from master branch."
    parameters {
        stringParam('COMMIT', 'HEAD', 'Commit to build')
    }
    scm {
        git {
            remote {
                url(gitUrl)
                credentials("0f57727d-e2bf-42ef-bdc5-94a53d7248c1")
            }
            extensions {
                wipeOutWorkspace()
                localBranch master
            }
        }
    }
    steps {
        shell "Look: I'm building master!"
    }
}