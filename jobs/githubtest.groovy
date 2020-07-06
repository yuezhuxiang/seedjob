import utils.*

new BtcGithubPipeline()
    .viewPrefix("core-services")
    .projectKey("core-services")
    .repository("jenkinsci-example")
    .includes("*")
    .build(this)