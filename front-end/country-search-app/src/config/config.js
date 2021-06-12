const BASE_URL = `http://localhost:8080/api`;
const PHONE_NUMBER = `countryPrefixAndNumber`
const acceptType = "application/json";

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
    PHONE_NUMBER
};