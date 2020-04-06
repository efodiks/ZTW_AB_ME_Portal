import {useState} from "react";


export const useFormState = initialState => {
    const [formState, setFormState] = useState(initialState);

    const onChange = e => {
        const {name, value} = e.target;
        setFormState((prevState => {
            return {
                ...prevState,
                [name]: value
            }
        }))
    };

    return [
        formState, onChange
    ]
};