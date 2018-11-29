
<p align="center">
  <a href="https://humanapi.co">
    <img width="400" src="https://firebasestorage.googleapis.com/v0/b/health-score-6740b.appspot.com/o/development%2Fresources%2Fimages%2Fhumanapi.png?alt=media&token=ebf36a25-eece-43ca-8431-357656e8ad16"><br/>
  </a>
  <h2 align="center">React Native Human API</h2>
</p>

<p align="center">
  <a href="https://www.npmjs.com/package/react-native-human-api"><img src="https://img.shields.io/npm/dt/react-native-human-api.svg?style=flat-square" alt="NPM downloads"></a>
  <a href="https://www.npmjs.com/package/react-native-human-api"><img src="https://img.shields.io/npm/v/react-native-human-api.svg?style=flat-square" alt="NPM version"></a>
  <a href="/LICENSE"><img src="https://img.shields.io/aur/license/yaourt.svg?style=flat-square" alt="License"></a>
  <a href="https://twitter.com/citizenhealth"><img src="https://img.shields.io/twitter/follow/CitizenHealthio.svg?style=social&logo=twitter&label=Follow" alt="Follow on Twitter"></a>
</p>

## Introduction

This is a React Native native module inspired by [wayneholis/react-native-humanapi](https://github.com/wayneholis/react-native-humanapi) library.

The Human API SDK has been updated to the latest build. We will be constantly updating this package for our own project. 

# react-native-react-native-human-api

## Getting started

`$ npm install react-native-human-api --save`

### Mostly automatic installation

`$ react-native link react-native-human-api`

#### Android
Even with the automatic installation there are remaining steps to finishing the Android import

 1. Add the following lines to `android/settings.gradle` :
```
  	include ':humanapi-sdk'  
  	project(':humanapi-sdk').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-human-api/android/humanapi-sdk')
```

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-react-native-human-api` and add `RNReactNativeHumanApi.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNReactNativeHumanApi.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add ` import io.citizenhealth.humanapi.RNReactNativeHumanApiPackage;` to the imports at the top of the file
  - Add `new RNReactNativeHumanApiPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-human-api'
  	project(':react-native-human-api').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-human-api/android')
  	include ':humanapi-sdk'  
  	project(':humanapi-sdk').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-human-api/android/humanapi-sdk')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-human-api')
  	```

## Usage

### 1. Create a Human API account

Go to https://developer.humanapi.co/signup and register for an account.

### 2. React Native
First install the package 

```
npm i react-native-human-api
react-native link react-native-human-api
```

Then import the component and render a button to open it.
```javascript
import RNHumanAPI from 'react-native-human-api';

// Call the API with a connect function
render() {
...
<TouchableOpacity  style={styles.button}  onPress={this.connectHumanAPI}>
	<Text  style={styles.instructions}>
		Connect HumanAPI
	</Text>
</TouchableOpacity>
...
}
```

Add your client id, secret and app key. You can get those from https://developer.humanapi.co under the settings tab in your Human API account. Also add the Human API redirect URLs.
```javascript
const  baseURL = 'https://connect.humanapi.co/embed?';
const  clientID = 'xxxx'; //Add your clientId here
const  clientSecret = 'xxx'; //Add your client secret here
const  appKey = 'xxx'; // Add your App Key here
const  finishURL = 'https://connect.humanapi.co/blank/hc-finish';
const  closeURL = 'https://connect.humanapi.co/blank/hc-close';
const authURL = 'https://xxx' // Add your backend auth endpoint
```
Then implement the call. Here, you need to store and retrieve the user public token to either create a user account or if the token exists, just login to the user Human API data. I am assuming in the example below that the *public_token* and the *client_user_id* are a component property. This should be part of your user account saved data. *public_token = null* if the user has not connected for the first time yet. 
```javascript
connectHumanAPI  = () => {
	const {
		public_token,
		client_user_id
	}  = this.props.user;

	const  humanAPI  =  new  RNHumanAPI()
	const  options  = (public_token) ? {
		client_id:  clientID,
		client_user_id:  client_user_id,
		public_token:  public_token,
		auth: (data) =>  this.sendAuth(data),
		auth_url:  authURL,
		success: (data) => {
			console.log(`Human API Callback: ${data}`);
			// save publicToken in your user account
			this._savePublicToken(data.public_token);
		}, // callback when success with auth_url
		cancel: () =>  
			console.log('cancel') // callback when 	cancel
		} : {
		client_id:  clientID,
		client_user_id:  client_user_id,
		auth: (data) =>  this.sendAuth(data),
		auth_url:  authURL,
		success: (data) => {
			// save publicToken
			this._savePublicToken(data.public_token);
		}, // callback when success with auth_url
		cancel: () =>  
			console.log('cancel') // callback when cancel
		}
		humanAPI.onConnect(options)
	}
}
```

## Backend server

A backend endpoint is required to handle the auth redirect and handshake with the Human API server. 

Here is a Google Firebase example of a simple Node.js microservice to deploy for this purpose:

```javascript
// Human API auth handler service on Google Firebase

const request = require('request');
exports.humanAPIHandshake = function(req, res, database, callback) {
	var sessionTokenObject = req.body;
	sessionTokenObject.clientSecret = 'xxx'; // Your Human API account client secret

	request({
		method: 'POST',
		uri: 'https://user.humanapi.co/v1/connect/tokens',
		json: sessionTokenObject
	}, (err, resp, body) => {
		if(err) {
			console.log(`error = ${err}`);
			callback(false);
			return;
		}

		// This is the response from the Human API server after authentication
		console.log("clientId ="+ body.clientId);
		console.log("humanId = " + body.humanId);
		console.log("accessToken = "+ body.accessToken);
		console.log("publicToken = "+ body.publicToken);

		//Send back publicToken to app
		var responseJSON = {
			humanId: body.humanId,
			accessToken: body.accessToken,
			publicToken: body.publicToken
		};

		callback(true, responseJSON);
		return;
    });
}
```

## Support

Please send any bug or comment to dev@citizenhealth.io

## License

* See [LICENSE](/LICENSE)