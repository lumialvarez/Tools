pipeline {
	agent any
	tools {
		maven 'Maven'
	}
	stages {
		stage('Get Version') {
			steps {
				script {
					MAVEN_VERSION = sh (
						script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout",
						returnStdout: true
					).trim()
				}
				script {
					currentBuild.displayName = "#" + currentBuild.number + " - v" + MAVEN_VERSION
				}
			}
		}
		stage('Test') {
			steps {	
				sh 'mvn clean test'
			}
			post {
				success {
					junit 'target/surefire-reports/**/*.xml' 
				}
			}
		}
		stage('Build') {
			steps {
				sh 'mvn clean package -DskipTests'
			}
		}
		stage('Deploy') {
			steps {
				sh 'mvn deploy -DskipTests -s /var/jenkins_home/settings.jfrog.xml'
			}
		}
	}
}