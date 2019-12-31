//
//  ViewRequestsTableViewController.swift
//  TubeIOS
//
//  Created by Anshul Ahluwalia on 12/22/19.
//  Copyright © 2019 Anshul Ahluwalia. All rights reserved.
//

import UIKit
import FirebaseDatabase

class Request: CustomDebugStringConvertible {
    
    
    public var className: String
    public var dateTime: String
    public var description: String
    public var userUID: String
    
    init(className: String, dateTime: String, description: String, userUID: String) {
        self.className = className
        self.dateTime = dateTime
        self.description = description
        self.userUID = userUID
    }
    var debugDescription: String {return "className: \(className), dateTime: \(dateTime), description: \(description)" }
}
class ViewRequestsTableViewController: UITableViewController {
    public var requestStrings: [String] = [String]() //This will contain everything that needs to be put into the table rows
    public var requests: [Request] = [Request]() //This contains the physical request objects
    public var requestKeys: [String] = [String]() //This contains all the node IDs of the requests from the Firebase Database
    override func viewDidLoad() {
        /*
         This function loads requests by going to the firebase database and loading every
         request under Unmatched Requests that matches any class listed under the current user's profile
         */
        super.viewDidLoad()
        self.tableView.register(UITableViewCell.self, forCellReuseIdentifier: "requestCell") //Registering the cells so we can edit them
        User.ref.child("requests").child("Unmatched Requests").observeSingleEvent(of: .value, with: { (snapshot) in
            let value = snapshot.value as? NSDictionary //This dictionary contains all the user UIDs
            for key in (value?.allKeys)! {
                let requestDictionary = value?[key] as? NSDictionary ?? [:] //This dictionary will contain every request made by a certain user UID
                print("Request Dictionary: \(requestDictionary)")
                for requestKey in (requestDictionary.allKeys) {
                    let request = requestDictionary[requestKey] as? NSDictionary ?? [:]
                    let className = request["className"] as? String ?? ""
                    if (User.classesList.contains(className)) {
                        let dateTime = request["dateTime"] as? String ?? ""
                        let description = request["description"] as? String ?? ""
                        let userUID = request["userUID"] as? String ?? ""
                        let requestObject = Request(className: className, dateTime: dateTime, description: description, userUID: userUID)
                        let requestString = className + ": " + dateTime
                        self.requests.append(requestObject)
                        self.requestStrings.append(requestString)
                        self.requestKeys.append((requestKey as? String ?? ""))
                    }
                }
                
            }
            self.tableView?.reloadData()
            //self.tableView?.reloadData()
        }) { (error) in
            print (error.localizedDescription)
        }
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return self.requestStrings.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "requestCell", for: indexPath)
        cell.textLabel?.text = requestStrings[indexPath.row]
        // Configure the cell...
        return cell
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let request = requests[indexPath.row]
        writeRequestToDatabase(className: request.className, dateTime: request.dateTime, description: request.description, reqSelected: true, uidToUse: request.userUID) //Writes the request to the matched request node
        let keyToRemove = requestKeys[indexPath.row]
        let dbRefToReq = User.ref.child("requests").child("Unmatched Requests").child(request.userUID).child(keyToRemove)
        dbRefToReq.setValue(nil) //Removes the value at this node by setting it to nil
        /*
         Following statements clear the current request in various formats
         */
        requestKeys.remove(at: indexPath.row)
        requests.remove(at: indexPath.row)
        requestStrings.remove(at: indexPath.row)
        tableView.deleteRows(at: [indexPath], with: .left)
        //print(request)
        
    }
    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
