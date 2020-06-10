# Live-Internet-Observer

Live Internet Observer Library

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.niraj-prajapati:Live-Internet-Observer:1.0'
	}


# Usage

Add this in your `Application` file

    override fun onCreate() {
        super.onCreate()
        NetworkConnectivityObserver.init(this)
    }
    
Use this in your `Activity` or in `BaseActivity` if you want to use it multiple places

    NetworkConnectivityObserver.internetAvailable.observe(this, Observer {
        it?.let {
            val status = if (it) "Connected" else "Disconnected"
            Log.d("TAG", "Connection Status: $status")
        }
    })
