import React from 'react'
import { Switch, Route } from 'react-router-dom'

//pages
import Home from './homepage.jsx'	
import Albums from './albumspage.jsx'
import Manage from './managepage.jsx'

class Main extends React.Component {
	render() {
		return (
			<main>
				<Switch>
					<Route exact path='/' component={Home}/>
					<Route exact path='/albums' component={Albums}/>
					<Route exact path='/manage' component={Manage}/>
				</Switch>
			</main>
		);
	}
}

export default Main