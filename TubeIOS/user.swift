//
//  user.swift
//  TubeIOS
//
//  Created by Anshul Ahluwalia on 11/16/19.
//  Copyright © 2019 Anshul Ahluwalia. All rights reserved.
//

import Foundation

class User {
    var GTID: Int
    var name: String
    var password: String
    var classes: [String]
    init(GTID: Int, name: String, password: String, classes: [String]) {
        self.GTID = GTID
        self.name = name
        self.password = password
        self.classes = classes
    }
    
}
