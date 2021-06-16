const BASE_URL = `http://localhost:8080/api`;
const PHONE_NUMBER = `countryPrefixAndNumber`
const acceptType = "application/json";
const NOT_FOUND_MESSAGE = "The country call-code of the number entered was not found, Hence we could not retrieve the country.";

const fetchOptionsPost = (body) => {
    return {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accept": acceptType
        },
        body: body

    };
};

export default {
    BASE_URL,
    fetchOptionsPost,
    PHONE_NUMBER,
    NOT_FOUND_MESSAGE
};