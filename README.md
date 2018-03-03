
<p align="center">
  <a href="https://humanapi.co">
    <img src="https://firebasestorage.googleapis.com/v0/b/health-score-6740b.appspot.com/o/development%2Fresources%2Fimages%2Fhumanapi.png?alt=media&token=ebf36a25-eece-43ca-8431-357656e8ad16"><br/>
  </a>
  <h2 align="center">React Native Human API</h2>
</p>

<p align="center">
  <a href="https://www.npmjs.com/package/react-native-human-api"><img src="https://img.shields.io/npm/dt/react-native-human-api.svg?style=flat-square" alt="NPM downloads"></a>
  <a href="https://www.npmjs.com/package/react-native-human-api"><img src="https://img.shields.io/npm/v/react-native-human-api.svg?style=flat-square" alt="NPM version"></a>
  <a href="/LICENSE"><img src="https://img.shields.io/aur/license/yaourt.svg?style=flat-square" alt="License"></a>
  <a href="https://twitter.com/citizenhealth"><img src="https://img.shields.io/twitter/follow/citizenhealth.svg?style=social&logo=twitter&label=Follow" alt="Follow on Twitter"></a>
</p>

## Introduction

This is a React Native native module based on  [wayneholis/react-native-humanapi](https://github.com/wayneholis/react-native-humanapi) library.

The Human API SDK has been updated to the latest.

# react-native-react-native-human-api

## Getting started

`$ npm install react-native-human-api --save`

### Mostly automatic installation

`$ react-native link react-native-human-api`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-react-native-human-api` and add `RNReactNativeHumanApi.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNReactNativeHumanApi.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `io.citizenhealth.humanapi.RNReactNativeHumanApiPackage;` to the imports at the top of the file
  - Add `new RNReactNativeHumanApiPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-human-api'
  	project(':react-native-human-api').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-human-api/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-human-api')
  	```

## Usage

First import the component and render a button to open it.
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
  Then implement the call.
```javascript
sendAuth  = (data) => {
	// send for auth_url with additional info
	console.log('auth')
	console.log(data)
	// data.client_id
	// data.human_id
	// data.session_token
}

connectHumanAPI  = () => {
	const  humanAPI  =  new  RNHumanAPI()
	const  options  = {
		client_id:  '<YOUR CLIENT ID HERE>',
		client_user_id:  'user@email.com',
		//public_token: 'bfcfa37fc10b7a7c31d3104b67605a83',
		// custom auth handle without auth_url
		auth: (data) =>  this.sendAuth(data),
		//auth_url: 'AUTH\_URL',
		success: (data) =>  console.log(data.public_token), // callback when success with auth_url
		cancel: () =>  console.log('cancel') // callback when cancel
	}
	humanAPI.onConnect(options)
}
```
## License

* See [LICENSE](/LICENSE)
