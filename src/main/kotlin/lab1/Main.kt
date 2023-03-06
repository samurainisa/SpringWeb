package lab1

data class Contact(val name: String, val phoneNumber: String)
class MobilePhone(private val myNumber: String) {
    private val myContacts = ArrayList<Contact>()

    fun addNewContact(newContact: Contact): Boolean {
        if (findContact(newContact) >= 0) {
            println("Contact is already on file.")
            return false
        }
        myContacts.add(newContact)
        return true
    }

    fun updateContact(oldContact: Contact, newContact: Contact): Boolean {
        val foundPosition = findContact(oldContact)
        if (foundPosition < 0) {
            println("${oldContact.name} was not found.")
            return false
        } else if (findContact(newContact) != -1) {
            println("Contact with name ${newContact.name} already exists. Update not successful.")
            return false
        }
        myContacts[foundPosition] = newContact
        println("${oldContact.name} was replaced with ${newContact.name}.")
        return true
    }

    fun removeContact(contact: Contact): Boolean {
        val foundPosition = findContact(contact)
        if (foundPosition < 0) {
            println("${contact.name} was not found.")
            return false
        }
        myContacts.removeAt(foundPosition)
        println("${contact.name} was deleted.")
        return true
    }

    private fun findContact(contact: Contact): Int {
        return myContacts.indexOf(contact)
    }

    fun queryContact(name: String): Contact? {
        for (i in myContacts.indices) {
            val contact = myContacts[i]
            if (contact.name == name) {
                return contact
            }
        }
        return null
    }

    fun printContacts() {
        println("Contact List")
        for (i in myContacts.indices) {
            println("${i + 1}. ${myContacts[i].name} -> ${myContacts[i].phoneNumber}")
        }
    }
}

fun main() {
    val mobilePhone = MobilePhone("1234567890")
    mobilePhone.addNewContact(Contact("Андрей", "9876543210"))
    mobilePhone.addNewContact(Contact("Никита", "8885554444"))
    mobilePhone.addNewContact(Contact("Петька", "1234567890"))

    mobilePhone.printContacts()

    println("----------------------------------------------------------------------------------")

    mobilePhone.updateContact(Contact("Никита", "8885554444"), Contact("Никита Садовиков", "8885555555"))
    mobilePhone.printContacts()
    println("----------------------------------------------------------------------------------")

    mobilePhone.removeContact(Contact("Петька", "1234567890"))
    mobilePhone.printContacts()
    println("----------------------------------------------------------------------------------")

    println(mobilePhone.queryContact("Андрей Крючков"))
    println(mobilePhone.queryContact("Андрей"))
}