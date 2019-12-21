//
//  User.swift
//  
//
//  Created by Anshul Ahluwalia on 11/23/19.
//

import Foundation
import FirebaseDatabase
import FirebaseAuth
class User {
    public static var firstName: String = ""
    public static var GTID: String = ""
    public static var email: String = ""
    public static var major: String = ""
    public static var password: String = ""
    public static var classesList: [String] = [String]()
    public static var ref: DatabaseReference!
    public static var userFieldsFilled: Bool = false
    
}
public func writeToDatabase(firstName: String, GTID: String, email: String, major: String, password: String, classesList: [String]) {
    var classesString = ""
    for className in classesList {
        classesString.append(className + ", ")
    }
    
    var userDictionary: [String: String] = [:]
    userDictionary["firstName"] = firstName
    userDictionary["GTID"] = GTID
    userDictionary["email"] = email
    userDictionary["major"] = major
    userDictionary["password"] = password
    userDictionary["classes"] = classesString
    User.ref.child("user").child((Auth.auth().currentUser?.uid)!).setValue(userDictionary)
}
				
