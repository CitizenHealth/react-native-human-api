/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */

import React, { Component } from 'react';
import RNHumanAPI from 'react-native-human-api';
import {
  StyleSheet,
  Text,
  View,
  TouchableOpacity
} from 'react-native';

class App extends Component {
  sendAuth = (data) => {
    // send for auth_url with additional info
    console.log('auth')
    console.log(data)
    // data.client_id
    // data.human_id
    // data.session_token
  }
  connectHumanAPI = () => {
    const humanAPI = new RNHumanAPI()
    const options = {
      client_id: 'b2fd0a46e2c6244414ef4133df6672edaec378a1',
      client_user_id: 'nabyl@wavou.com',
      //public_token: 'bfcfa37fc10b7a7c31d3104b67605a83',
      // custom auth handle without auth_url
      auth: (data) => this.sendAuth(data),
      //auth_url: 'AUTH_URL',
      success: (data) => console.log(data.public_token),  // callback when success with auth_url
      cancel: () => console.log('cancel')  // callback when cancel
    }
    humanAPI.onConnect(options)
  }
  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <Text style={styles.instructions}>
          To get started, edit index.android.js
        </Text>
        <Text style={styles.instructions}>
          Shake or press menu button for dev menu
        </Text>
        <TouchableOpacity style={styles.button} onPress={this.connectHumanAPI}>
          <Text style={styles.instructions}>
            Connect HumanAPI
          </Text>
        </TouchableOpacity>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#fff',
    marginBottom: 5,
  },
  button: {
    padding: 5,
    borderRadius: 5,
    backgroundColor: '#337ab7',
  },
});

export default App
