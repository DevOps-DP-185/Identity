import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.MavenBuildStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

version = "2019.2"

project {
    buildType(IdentityBuild)
    buildType(Deploy)
}

object IdentityBuild : BuildType({
    name = "Build"

    vcs {
        root(DslContext.settingsRoot)
    }
    steps {
        maven {
            name = "Identity-starter"
            goals = "clean install"
            pomLocation = "identity-starter/pom.xml"
            localRepoScope = MavenBuildStep.RepositoryScope.MAVEN_DEFAULT
            jdkHome = "%env.JDK_11%"
        }
        maven {
            name = "Identity-service"
            goals = "clean package"
            pomLocation = "identity-service/pom.xml"
            localRepoScope = MavenBuildStep.RepositoryScope.MAVEN_DEFAULT
            jdkHome = "%env.JDK_11%"
        }
        dockerCommand {
         commandType = build {
                source = file {
                    path = "identity-service/Dockerfile"
                }
                namesAndTags = "artemkulish/demo4:identity"
            }
            param("dockerImage.platform", "linux")
        }
        dockerCommand {
            commandType = push {
                namesAndTags = "artemkulish/demo4:identity"
            }
            param("dockerfile.path", "Dockerfile")
        }
    }
    triggers {
        vcs {
        }
    }
    features {
        dockerSupport {
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_5"
            }
        }
    }        
})

object Deploy : BuildType({
    name = "Deploy"
    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    vcs {
        root(DslContext.settingsRoot)
    }
    steps {
        step {
            type = "ssh-exec-runner"
            param("jetbrains.buildServer.sshexec.command", "ls")
            param("jetbrains.buildServer.deployer.targetUrl", "35.184.252.223")
            param("jetbrains.buildServer.sshexec.authMethod", "DEFAULT_KEY")
            param("jetbrains.buildServer.sshexec.keyFile", "/home/artemkulish123/")
        }
    }
    triggers {
        vcs {
        }
    }
    dependencies {
        snapshot(IdentityBuild) {
        }
    }
})



