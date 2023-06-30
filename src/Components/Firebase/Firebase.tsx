// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { getDownloadURL, getStorage, ref, uploadBytes } from 'firebase/storage';
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyAwXDQC_PTcFOvhty3SRkqBrYxuCjJ9vRw",
  authDomain: "miaudotecao.firebaseapp.com",
  projectId: "miaudotecao",
  storageBucket: "miaudotecao.appspot.com",
  messagingSenderId: "280546633136",
  appId: "1:280546633136:web:ca9fa6df5e9957fb0c6275",
  measurementId: "G-HPGZS9LDQ8"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
const storage = getStorage(app);
const storageRef = ref(storage);

export { storageRef, uploadBytes, getDownloadURL };
