import React from 'react'
import Header from './components/header.jsx'
import Footer from './components/footer.jsx'
import Main from './pages/main.jsx'

class App extends React.Component {
	render() {
		return (
			<div>
				<Header />
				<Main />
				<Footer />
			</div>
		);
	}
}

export default App