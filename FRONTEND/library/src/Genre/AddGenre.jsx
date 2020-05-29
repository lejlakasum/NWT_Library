import React from 'react';
import "./Genre.css"

class AddGenre extends React.Component {
    render() {
        return (
            <div >
                <form className="add-genre">
                    <input type="text" placeholder="Genre name" />
                    <input type="submit" value="Add" />
                </form>
            </div>
        )
    }
}

export default AddGenre