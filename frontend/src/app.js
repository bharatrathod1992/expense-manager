import React,{Component} from 'react';
import Account from './components/account'

export default class App extends Component{

getAccounts=()=>{
    console.log("ITS GETTING CALLED ...")
    return(<Account />)
}

render(){
    return(
        <div>
            <Account />
        </div>
    )
}
}