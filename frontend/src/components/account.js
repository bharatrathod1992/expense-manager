import React,{Component} from 'react';
const axios = require('axios');

export default class Account extends Component{

    constructor(){
        super();
    }

    componentDidMount(){
        axios.post('/account/accounts')
        .then(response => {
            console.log("ACCOUNTS : ", response)
        })
        .then(error => {
            console.log("ERROR : ", error)
        })
    }

    render() {
        console.log("-------");
        return (
            <h1> THIS IS ACCOUNT </h1>
        )
    }
}