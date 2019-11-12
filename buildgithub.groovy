import utilities.GithubMultibranch

def multiPipeline = new GithubMultibranch()
    .description('Just try make world better')
    .name('Github-Test')
    .displayName('Github-Test')
    .repositoryOwner("yuezhuxiang")
    .repositoryName("jenkinsci-example")
    .credentials('0f57727d-e2bf-42ef-bdc5-94a53d7248c1')
    .includeBranches('development master branchA')
    .excludeBranches('')
    .build(this)