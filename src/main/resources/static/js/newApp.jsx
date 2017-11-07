/*-----COMMON PAGE COMPONENTS-----*/
	/*-----Header-----*/
		class Header extends React.Component {
			render() {
				return (
					<header>
						<a href="#" id="logo"></a>
						<nav>
						</nav>
						<div className="clear"></div>
					</header>
				);
			}
		}

	/*-----Footer-----*/
		class Footer extends React.Component {
			render() {
				return (
					<footer>
						<p>Kim Brygger (c)</p>
					</footer>
				);
			}
		}

/*-----PAGE CONTENT-----*/
	
		class Main extends React.Component {
			render() {
				return (
					<main>

					</main>
				);
			}
		}
	/*-----Home Page-----*/
		class Home extends React.Component {
			render() {
				return (
					<div>
						<h2>Welcome</h2>
						<p>Kuvia</p>
					</div>
				);
			}
		}

	/*-----Albums Page-----*/
		class Albums extends React.Component {
			render() {
				return (
					<div>
						<h2>STUFF</h2>
						<p>Mauris sem velit, vehicula eget sodales vitae,
						rhoncus eget sapien:</p>
						<ol>
							<li>Nulla pulvinar diam</li>
							<li>Facilisis bibendum</li>
							<li>Vestibulum vulputate</li>
							<li>Eget erat</li>
							<li>Id porttitor</li>
						</ol>
					</div>
				);
			}
		}

class Manage extends React.Component {
  constructor(props) {
      super(props);
      this.deleteImage = this.deleteImage.bind(this);
      this.createImage = this.createImage.bind(this);
      this.state = {
          images: [],
      };
   }
 
  componentDidMount() {
    this.loadImagesFromServer();
  }
  
  // Load images from database
  loadImagesFromServer() {
      fetch('http://localhost:8080/api/images', {credentials: 'same-origin'}) 
      .then((response) => response.json()) 
      .then((responseData) => { 
          this.setState({ 
              images: responseData._embedded.images, 
          }); 
      });     
  } 
  
  // Delete image
  deleteImage(image) {
      fetch (image._links.self.href,
      {method: 'DELETE', credentials: 'same-origin'})
      .then( 
          res => this.loadImagesFromServer()
      )             
  }  
  
  // Create new image
  createImage(image) {
      fetch('http://localhost:8080/api/images', {
          method: 'POST', credentials: 'same-origin',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(image)
      })
      .then( 
          res => this.loadImagesFromServer()
      )
  }
  
  render() {
    return (
       <div>
          <ImageForm createImage={this.createImage}/>
          <ImageTable deleteImage={this.deleteImage} images={this.state.images}/> 
       </div>
    );
  }
}
    	
class ImageTable extends React.Component {
    constructor(props) {
        super(props);
    }
    
    render() {
    var images = this.props.images.map(image =>
        <Image key={image._links.self.href} image={image} deleteImage={this.props.deleteImage}/>
    );

    return (
      <div>
      <table className="table table-striped">
        <thead>
          <tr>
            <th>Game</th>
			<th>Name</th>
			<th>Date</th>
			<th>url</th>
			<th>tags</th>
			<th></th>
          </tr>
        </thead>
        <tbody>{images}</tbody>
      </table>
      </div>);
  }
}
        
class Image extends React.Component {
    constructor(props) {
        super(props);
        this.deleteImage = this.deleteImage.bind(this);
    }

    deleteImage() {
        this.props.deleteImage(this.props.image);
    } 
 
    render() {
        return (
          <tr>
            <td>{this.props.image.game}</td>
            <td>{this.props.image.name}</td>
            <td>{this.props.image.date}</td>
            <td>{this.props.image.url}</td>
            <td>{this.props.image.tags}</td>
            <td>
                <button className="btn btn-danger" onClick={this.deleteImage}>Delete</button>
            </td>
          </tr>
        );
    } 
}

class ImageForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
			game: '',
			name: '',
			date: '',
			url: '',
			tags: ''
		};
        this.handleSubmit = this.handleSubmit.bind(this);   
        this.handleChange = this.handleChange.bind(this);     
    }

    handleChange(event) {
        this.setState(
            {[event.target.name]: event.target.value}
        );
    }    
    
    handleSubmit(event) {
        event.preventDefault();
        var newImage = {
			game: this.state.game,
			name: this.state.name,
			date: this.state.date,
			url: this.state.url,
			tags: this.state.tags
		};
        this.props.createImage(newImage);        
    }
    
    render() {
        return (
            <div className="panel panel-default">
                <div className="panel-heading">Create image</div>
                <div className="panel-body">
                <form className="form-inline">
                    <div className="col-md-2">
                        <input type="text" placeholder="Game" className="form-control"  name="game" onChange={this.handleChange}/>    
                    </div>
                    <div className="col-md-2">       
                        <input type="text" placeholder="Name" className="form-control" name="name" onChange={this.handleChange}/>
                    </div>
                    <div className="col-md-2">
                        <input type="text" placeholder="Date" className="form-control" name="date" onChange={this.handleChange}/>
                    </div>
					<div className="col-md-2">
                        <input type="text" placeholder="url" className="form-control" name="url" onChange={this.handleChange}/>
                    </div>
					<div className="col-md-2">
                        <input type="text" placeholder="tags" className="form-control" name="tags" onChange={this.handleChange}/>
                    </div>
                    <div className="col-md-2">
                        <button className="btn btn-success" onClick={this.handleSubmit}>Save</button>   
                    </div>        
                </form>
                </div>      
            </div>
         
        );
    }
}

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

ReactDOM.render((
	<App />/*
	<BrowserRouter>
	</BrowserRouter>*/
	), document.getElementById('root')
);
